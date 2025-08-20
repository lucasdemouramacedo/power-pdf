package com.power.power_pdf.service;

import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.entity.MergeRequestFile;
import com.power.power_pdf.exceptions.MergeRequestCreationException;
import com.power.power_pdf.repository.MergeRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public MergeRequest makeMergeRequest(MergeRequest mergeRequest, List<MultipartFile> files) {
        try {
            MergeRequest mergeRequestSaved = this.save(mergeRequest);

            for (MultipartFile file : files) {
                uploadFile(file, mergeRequestSaved);
            }

            return mergeRequestSaved;
        } catch (IOException e) {
            log.error("Error (MergeRequestService.makeMergeRequest): ", e);
            throw new MergeRequestCreationException("Erro ao fazer upload dos arquivos!");
        } catch (Exception e) {
            log.error("Error (MergeRequestService.makeMergeRequest): ", e);
            throw new MergeRequestCreationException();
        }
    }

    private void uploadFile(MultipartFile file, MergeRequest mergeRequest) throws IOException {
        String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String objectId = UUID.randomUUID().toString() + "_" + baseName;

        MergeRequestFile mergeRequestFile = new MergeRequestFile(mergeRequest, baseName, objectId);
        MergeRequestFile mergeRequestFileSaved = mergeRequestFileService.save(mergeRequestFile);

        storageService.upload(
                "requestsfiles",
                file.getOriginalFilename(),
                file.getInputStream(),
                file.getContentType()
        );
    }
}
