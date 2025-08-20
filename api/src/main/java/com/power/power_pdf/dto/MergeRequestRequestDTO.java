package com.power.power_pdf.dto;

import com.power.power_pdf.entity.MergeRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MergeRequestRequestDTO {

    private String fileName;
    private List<MultipartFile> files;

    public MergeRequestRequestDTO() {}

    public MergeRequestRequestDTO(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<MultipartFile> getFiles() {
        return this.files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public MergeRequest toMergeRequestObject() {
        return new MergeRequest(this.fileName);
    }
}
