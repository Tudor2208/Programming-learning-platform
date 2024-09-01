package com.example.proiect.service.impl;

import com.example.proiect.model.Classroom;
import com.example.proiect.model.Homework;
import com.example.proiect.model.Problem;
import com.example.proiect.model.User;
import com.example.proiect.repository.HomeworkRepository;
import com.example.proiect.repository.UserRepository;
import com.example.proiect.service.HomeworkService;
import com.example.proiect.service.ProblemSolutionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class HomeworkServiceImpl implements HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final UserRepository userRepository;
    private final ProblemSolutionService problemSolutionService;

    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, UserRepository userRepository, ProblemSolutionService problemSolutionService) {
        this.homeworkRepository = homeworkRepository;
        this.userRepository = userRepository;
        this.problemSolutionService = problemSolutionService;
    }

    @Override
    public Homework createHomework(Homework homework) {
        return homeworkRepository.save(homework);
    }

    @Override
    public List<Homework> findAllHomeworks() {
        return (List<Homework>)homeworkRepository.findAll();
    }

    @Override
    public Homework findHomeworkById(Long id) {
        return homeworkRepository.findById(id).get();
    }

    @Override
    public List<Homework> findClassroomHomeworks(Long id) {
        return homeworkRepository.findAllByClassroomId(id);
    }

    @Override
    public Homework deleteHomework(Long id) {
        Homework homework = homeworkRepository.findById(id).get();
        homeworkRepository.delete(homework);
        return homework;
    }

    @Override
    public int probSolvedByAStudentFromHomework(Long userId, Long homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId).get();
        Set<Problem> problems = homework.getProblems();
        int solved = 0;
        for (Problem problem : problems) {
            int score = problemSolutionService.findMaxScoreByProblemAndUser(problem.getId(), userId);
            if(score == 100) {
                solved ++;
            }
        }

        return solved;
    }

    @Override
    public boolean expiredProblem(Long userId, Long problemId) {
        User user = userRepository.findById(userId).get();
        Set<Classroom> classrooms = user.getClassrooms();
        List<Homework> homeworks = (List<Homework>) homeworkRepository.findAll();
        for (Homework homework : homeworks) {
            Classroom classroom = homework.getClassroom();
            if(classrooms.contains(classroom)) {
                Set<Problem> problems = homework.getProblems();
                for (Problem problem : problems) {
                    if(Objects.equals(problem.getId(), problemId)) {
                        Date today = new Date();
                        if(homework.getDeadline().before(today)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}
