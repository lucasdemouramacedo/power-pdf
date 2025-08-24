package com.power.power_pdf.dto;

import java.time.LocalDate;

public class MergeRequestCountByDateDTO {

    private LocalDate date;
    private Long mergeRequestCount;

    public MergeRequestCountByDateDTO(LocalDate date, Long mergeRequestCount) {
        this.date = date;
        this.mergeRequestCount = mergeRequestCount;
    }

    public MergeRequestCountByDateDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getMergeRequestCount() {
        return mergeRequestCount;
    }

    public void setMergeRequestCount(Long mergeRequestCount) {
        this.mergeRequestCount = mergeRequestCount;
    }
}