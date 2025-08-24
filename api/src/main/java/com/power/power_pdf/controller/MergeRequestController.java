package com.power.power_pdf.controller;

import com.power.power_pdf.dto.DateRangeDTO;
import com.power.power_pdf.dto.FileDownloadDTO;
import com.power.power_pdf.dto.MergeRequestRequestDTO;
import com.power.power_pdf.dto.MergeRequestResponseDTO;
import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.service.MergeRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merge")
@Tag(name = "Mesclagem", description = "Gerenciamento de masclagem")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @PostMapping
    @Operation(summary = "Criar uma nova mesclagem de PDFs")
    public ResponseEntity<MergeRequestResponseDTO> create(@Valid @ModelAttribute MergeRequestRequestDTO dto) {

        MergeRequest mergeRequest = mergeRequestService.makeMergeRequest(dto.toMergeRequestObject(), dto.getFiles());
        MergeRequestResponseDTO response = new MergeRequestResponseDTO().fromEntity(mergeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Busca mesclagens feitas entre datas")
    public ResponseEntity<List<MergeRequestResponseDTO>> list(@Valid @ModelAttribute DateRangeDTO dto) {

        List<MergeRequestResponseDTO> response = mergeRequestService.getMergeRequests(dto.getStartDate(), dto.getEndDate());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Fazer download de um arquivo mesclado")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id) {
        FileDownloadDTO file = mergeRequestService.downloadFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .contentType(file.getMediaType())
                .body(file.getResource());
    }
}
