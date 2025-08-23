package com.power.power_pdf.dto;

import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.validation.PdfOnly;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MergeRequestRequestDTO {

    @NotBlank(message = "Adicione um nome para o arquivo mesclado.")
    @NotNull(message = "Adicione um nome para o arquivo mesclado.")
    private String fileName;

    @Size(min = 2, message = "Adicione pelo menos 2 arquivos para mesclar.")
    @PdfOnly(message = "Todos os arquivos devem ser PDFs.")
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
