package com.power.power_pdf.controller;

import com.power.power_pdf.dto.MergeRequestRequestDTO;
import com.power.power_pdf.dto.MergeRequestResponseDTO;
import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.service.MergeRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merge")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @PostMapping
    public ResponseEntity<MergeRequestResponseDTO> create(@RequestBody MergeRequestRequestDTO dto) {
        MergeRequest mergeRequest = mergeRequestService.save(dto.toMergeRequestObject());
        MergeRequestResponseDTO response = new MergeRequestResponseDTO().fromEntity(mergeRequest);

        return ResponseEntity.status(201).body(response);
    }
}
