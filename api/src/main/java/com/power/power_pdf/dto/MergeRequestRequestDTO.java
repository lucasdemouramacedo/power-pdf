package com.power.power_pdf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power.power_pdf.entity.MergeRequest;

public class MergeRequestRequestDTO {

    @JsonProperty("fileName")
    private String fileName;

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

    public MergeRequest toMergeRequestObject() {
        return new MergeRequest(this.fileName);
    }
}
