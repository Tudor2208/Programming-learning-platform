package com.example.proiect.controllers;

import com.example.proiect.model.enums.FileType;
import com.example.proiect.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/report")
@Controller
@CrossOrigin
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/{type}")
    public ResponseEntity generateReport(@PathVariable String type) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.exportReport(FileType.valueOf(type)));
    }
}
