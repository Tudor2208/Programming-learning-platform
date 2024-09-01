package com.example.proiect.service;

import com.example.proiect.model.enums.FileType;
import org.springframework.stereotype.Component;

@Component
public interface ReportService {
    String exportReport(FileType type);
}
