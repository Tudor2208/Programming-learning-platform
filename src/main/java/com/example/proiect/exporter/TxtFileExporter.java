package com.example.proiect.exporter;

public class TxtFileExporter implements FileExporter{
    @Override
    public String exportObject(Object o) {
        return o.toString();
    }
}
