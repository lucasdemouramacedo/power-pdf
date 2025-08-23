package com.power.power_pdf.controller;

import com.power.power_pdf.dto.FileDownloadDTO;
import com.power.power_pdf.dto.MergeRequestRequestDTO;
import com.power.power_pdf.dto.MergeRequestResponseDTO;
import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.service.MergeRequestService;

import jakarta.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/merge")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @PostMapping
    public ResponseEntity<MergeRequestResponseDTO> create(@Valid @ModelAttribute MergeRequestRequestDTO dto) {

        MergeRequest mergeRequest = mergeRequestService.makeMergeRequest(dto.toMergeRequestObject(), dto.getFiles());
        MergeRequestResponseDTO response = new MergeRequestResponseDTO().fromEntity(mergeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MergeRequestResponseDTO>> list(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate) {

        List<MergeRequestResponseDTO> response = mergeRequestService.getMergeRequests(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id) {
        FileDownloadDTO file = mergeRequestService.downloadFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .contentType(file.getMediaType())
                .body(file.getResource());
    }
}
