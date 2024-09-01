package com.example.proiect.service.impl;

import com.example.proiect.exporter.FileExporter;
import com.example.proiect.exporter.FileExporterFactory;
import com.example.proiect.model.Report;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.FileType;
import com.example.proiect.service.LoginActivityService;
import com.example.proiect.service.ProblemSolutionService;
import com.example.proiect.service.ReportService;
import com.example.proiect.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@NoArgsConstructor
public class ReportServiceImpl implements ReportService {
    @Autowired
    private UserService userService;

    @Autowired
    private ProblemSolutionService problemSolutionService;

    @Autowired
    private LoginActivityService loginActivityService;

    public ReportServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String exportReport(FileType type) {
        FileExporter fileExporter = FileExporterFactory.getFileExporter(type);
        int nrUsers = userService.findAllUsers().size();
        int nrUsersRegisteredToday = userService.countUsersRegisteredToday();
        int nrUsersRegisteredLastWeek = userService.countUsersRegisteredLastWeek();
        int nrUsersRegisteredLastMonth = userService.countUsersRegisteredLastMonth();
        User userWithTheMostSolutionsToday = problemSolutionService.findUserWithTheMostSolutionsToday();
        int nrLogged = loginActivityService.findAllLoggedInUsers().size();

        Report report = Report.builder()
                .currentDate(new Date())
                .nrOfUsers(nrUsers)
                .nrOfUsersRegisteredToday(nrUsersRegisteredToday)
                .nrOfUsersRegisteredLastWeek(nrUsersRegisteredLastWeek)
                .nrOfStudentsRegisteredLastMonth(nrUsersRegisteredLastMonth)
                .mostActiveUserToday(userWithTheMostSolutionsToday)
                .nrUsersConnected(nrLogged)
                .build();

        return fileExporter.exportObject(report);

    }
}