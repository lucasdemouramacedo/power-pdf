package com.power.power_pdf.controller;

import java.time.LocalDate;
import java.util.List;

import com.power.power_pdf.dto.DateRangeDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.power.power_pdf.dto.MergeRequestCountByDateDTO;
import com.power.power_pdf.service.ReportMergeRequestService;

@RestController
@RequestMapping("/report")
public class ReportMergeRequestController {

    private final ReportMergeRequestService reportMergeRequestService;

    public ReportMergeRequestController(ReportMergeRequestService reportMergeRequestService) {
        this.reportMergeRequestService = reportMergeRequestService;
    }

    @GetMapping
    @Operation(summary = "Retorna a contagem de mesclagens por dia")
    public ResponseEntity<List<MergeRequestCountByDateDTO>> list(@Valid @ModelAttribute DateRangeDTO dto) {

        List<MergeRequestCountByDateDTO> response = reportMergeRequestService.reportMergeRequestsByDate(dto.getStartDate(), dto.getEndDate());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
