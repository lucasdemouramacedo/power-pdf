package com.power.power_pdf.controller;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<MergeRequestCountByDateDTO>> list(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate) {

        List<MergeRequestCountByDateDTO> response = reportMergeRequestService.reportMergeRequestsByDate(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
