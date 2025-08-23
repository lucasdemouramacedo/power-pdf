package com.power.power_pdf.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { PdfOnlyValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PdfOnly {
    String message() default "Todos os arquivos devem ser PDFs.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
