package com.example.proiect.exporter;

import com.example.proiect.model.enums.FileType;

public class FileExporterFactory {

    public static FileExporter getFileExporter(FileType type){
        switch(type) {
            case TXT -> {
                return new TxtFileExporter();
            }
            default -> {return new XMLFileExporter();
            }
        }
    }
}
