package com.power.power_pdf.service;

import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.exceptions.MergeRequestCreationException;
import com.power.power_pdf.repository.MergeRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;
    private final MergeRequestFileService mergeRequestFileService;
    private final StorageService storageService;

    public MergeRequestService(MergeRequestRepository mergeRequestRepository,
                               MergeRequestFileService mergeRequestFileService,
                               StorageService storageService) {
        this.mergeRequestRepository = mergeRequestRepository;
        this.mergeRequestFileService = mergeRequestFileService;
        this.storageService = storageService;
    }

    public MergeRequest save(MergeRequest mergeRequest) {
        try {
            mergeRequest = mergeRequestRepository.save(mergeRequest);

            return mergeRequest;
        } catch (Exception e) {
            throw new MergeRequestCreationException();
        }
    }

    public MergeRequest makeMergeRequest(MergeRequest mergeRequest, List<MultipartFile> files) {
        try {
            mergeRequest = mergeRequestRepository.save(mergeRequest);

            files.forEach(f ->
                    {
                        try {
                            UUID uuid = UUID.randomUUID();
                            storageService.upload("requestsfiles", uuid + f.getOriginalFilename(), f.getInputStream(), f.getContentType());
                        } catch (IOException e) {
                            log.error("Error (MergeRequestService.makeMergeRequest.forEach): ", e.getMessage());
                        }
                    }
            );
            return mergeRequest;
        } catch (Exception e) {
            log.error("Error (MergeRequestService.makeMergeRequest): ", e.getMessage());
            throw new MergeRequestCreationException();
        }
    }
}
