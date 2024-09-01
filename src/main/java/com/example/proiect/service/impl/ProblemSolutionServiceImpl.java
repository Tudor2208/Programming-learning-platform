package com.example.proiect.service.impl;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemSolution;
import com.example.proiect.model.ProblemTest;
import com.example.proiect.model.User;
import com.example.proiect.repository.ProblemRepository;
import com.example.proiect.repository.ProblemSolutionRepository;
import com.example.proiect.repository.ProblemTestRepository;
import com.example.proiect.repository.UserRepository;
import com.example.proiect.service.ProblemSolutionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ProblemSolutionServiceImpl implements ProblemSolutionService {
    private final ProblemSolutionRepository problemSolutionRepo;
    private final ProblemTestRepository problemTestRepo;
    private final ProblemRepository problemRepo;
    private final UserRepository userRepo;

    public ProblemSolutionServiceImpl(ProblemSolutionRepository problemSolutionRepo, ProblemTestRepository problemTestRepo, ProblemRepository problemRepo, UserRepository userRepo) {
        this.problemSolutionRepo = problemSolutionRepo;
        this.problemTestRepo = problemTestRepo;
        this.problemRepo = problemRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ProblemSolution createProblemSolution(ProblemSolution solution) {
        return problemSolutionRepo.save(solution);
    }

    @Override
    public List<ProblemSolution> findAllProblemSolutions() {
        return (List<ProblemSolution>)problemSolutionRepo.findAll();
    }

    @Override
    public ProblemSolution findProblemSolutionById(Long id) {
        return problemSolutionRepo.findById(id).get();
    }

    @Override
    public ProblemSolution updateProblemSolution(Long id, ProblemSolution solution) {
        ProblemSolution problemSolutionToUpdate = problemSolutionRepo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("ProblemSolution with ID %d doesn't exist", id)));
        problemSolutionToUpdate.setProblem(solution.getProblem());
        problemSolutionToUpdate.setCode(solution.getCode());
        problemSolutionToUpdate.setScore(solution.getScore());
        problemSolutionToUpdate.setSubmissionDate(solution.getSubmissionDate());
        problemSolutionRepo.save(problemSolutionToUpdate);
        return problemSolutionToUpdate;
    }

    @Override
    public ProblemSolution deleteProblemSolution(Long id) {
      ProblemSolution solution = problemSolutionRepo.findById(id).get();
      problemSolutionRepo.delete(solution);
      return solution;
    }

    private static boolean pseudoWait(Process p) {
        while (true) {
            try {
                if (p.exitValue() == 0) {
                    p.destroy();
                    return true;
                } else {
                    return false;
                }
            } catch (IllegalThreadStateException e) {/* nothing */}
        }
    }

    private static void cleanupFiles() {
        List<File> files = new ArrayList<>();
        files.add(new File("test.in"));
        files.add(new File("test.out"));
        files.add(new File("code.c"));
        files.add(new File("output.exe"));

        for (File file : files) {
            if (file.exists()) {
                var ignore = file.delete();
            }
        }
    }

    @Override
    public int getScore(String code, Long problemID, Long userID) throws IOException {
        // Get all problem tests with problemID
        List<ProblemTest> problemTests = new ArrayList<>();
        List<ProblemTest> allTests = (List<ProblemTest>)problemTestRepo.findAll();
        for (ProblemTest test : allTests) {
            if(Objects.equals(test.getProblem().getId(), problemID)) {
                problemTests.add(test);
            }
        }

        // create .c file
        FileWriter myWriter = new FileWriter("code.c");
        myWriter.write(code);
        myWriter.close();

        // compile .c file
        ProcessBuilder compileProcess = new ProcessBuilder("gcc", "code.c", "-o", "output");
        compileProcess.redirectError(new File("tempError.txt"));
        if (!pseudoWait(compileProcess.start())) {
            return -1;
        }

        // run all tests
        myWriter = new FileWriter("test.in", false);
        myWriter.write(problemTests.get(0).getInput());
        myWriter.close();

        ProcessBuilder runProcess = new ProcessBuilder("output.exe");
        runProcess.redirectError(new File("tempError.txt"));
        if (!pseudoWait(runProcess.start())) {
            return -1;
        }

        //for each test, write the input of the test in "test.in"
        //run .exe
        //compare the actual output with the correct output
        //if equal => correctTests ++

        int correctTests = 0;
        for (ProblemTest problemTest : problemTests) {
            myWriter = new FileWriter("test.in", false);
            myWriter.write(problemTest.getInput());
            myWriter.close();

            if (!pseudoWait(runProcess.command("output.exe").start())) {
                return -1;
            }

            String output = Files.readString(Path.of("test.out"));
            String correctOut = problemTest.getOutput();
            if (output.equals(correctOut)) {
                correctTests ++;
            }
        }
        cleanupFiles();

        double score = ((double) correctTests / problemTests.size()) * 100;

        ProblemSolution solution = new ProblemSolution(new Date(), (int)score, code, problemRepo.findById(problemID).get(), userRepo.findById(userID).get());
        createProblemSolution(solution);
        return (int)score;
    }
    @Override
    public int findMaxScoreByProblemAndUser(Long problemID, Long userID) {
        try {
            List<ProblemSolution> allByProblemAndUser = problemSolutionRepo.findAllByProblemAndUser(problemRepo.findById(problemID).get(), userRepo.findById(userID).get());
            ProblemSolution ps = allByProblemAndUser.stream().max(Comparator.comparing(ProblemSolution::getScore)).get();
            return ps.getScore();
        }catch(NoSuchElementException ex) {
            return -1;
        }
    }

    @Override
    public User findUserWithTheMostSolutionsToday() {
        List<ProblemSolution> allSolutions = (List<ProblemSolution>)problemSolutionRepo.findAll();
        List<ProblemSolution> goodSolutions = new ArrayList<>();
        Set<String> uniqueKeys = new HashSet<>();
        Date date = new Date();

        for (ProblemSolution solution : allSolutions) {
            Date submissionDate = solution.getSubmissionDate();
            if (submissionDate.getDay() == date.getDay() &&
                    submissionDate.getMonth() == date.getMonth() &&
                    submissionDate.getYear() == date.getYear() &&
                    solution.getScore() == 100) {
                String key = solution.getUser().getId() + "-" + solution.getProblem().getId();
                if (!uniqueKeys.contains(key)) {
                    uniqueKeys.add(key);
                    goodSolutions.add(solution);
                }
            }
        }
        if(goodSolutions.size() == 0) {
            return null;
        }

        Map<User, Integer> myMap = new Hashtable<>();
        for (ProblemSolution goodSolution : goodSolutions) {
            User currentUser = goodSolution.getUser();
            if (myMap.containsKey(currentUser)) {
                int count = myMap.get(currentUser);
                myMap.put(currentUser, count + 1);
            } else {
                myMap.put(currentUser, 1);
            }
        }

        int maxx = 0;
        User bestUser = null;
        for (Map.Entry<User, Integer> userIntegerEntry : myMap.entrySet()) {
            if(userIntegerEntry.getValue() > maxx) {
                maxx = userIntegerEntry.getValue();
                bestUser = userIntegerEntry.getKey();
            }
        }
        return bestUser;

    }
}
