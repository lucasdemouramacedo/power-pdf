package com.power.power_pdf.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DateRangeDTO {

    @NotNull(message = "O campo startDate não pode estar vazio")
    private LocalDate startDate;

    @NotNull(message = "O campo endtDate não pode estar vazio")
    private LocalDate endDate;

    public DateRangeDTO() {
    }

    public DateRangeDTO(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
