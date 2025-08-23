package com.power.power_pdf.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PdfOnlyValidator implements ConstraintValidator<PdfOnly, List<MultipartFile>> {

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null) {
            return false;
        }

        return files.stream().allMatch(file -> 
            file != null && file.getOriginalFilename() != null &&
            file.getOriginalFilename().toLowerCase().endsWith(".pdf")
        );
    }
}