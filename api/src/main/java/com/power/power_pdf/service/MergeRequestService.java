package com.power.power_pdf.service;

import com.power.power_pdf.dto.MergeRequestResponseDTO;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<MergeRequestResponseDTO> getMergeRequests(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);
        List<MergeRequest> mergeRequests = mergeRequestRepository.findByCreatedAtBetween(startDateTime, endDateTime);
        List<MergeRequestResponseDTO> dtos = mergeRequests.stream()
                .map(mergeRequest -> new MergeRequestResponseDTO().fromEntity(mergeRequest))
                .collect(Collectors.toList());
        return dtos;
    }

    private void uploadFile(MultipartFile file, MergeRequest mergeRequest) throws IOException {
        String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String objectId = UUID.randomUUID().toString() + "_" + baseName;

        MergeRequestFile mergeRequestFile = new MergeRequestFile(mergeRequest, baseName, objectId);
        MergeRequestFile mergeRequestFileSaved = mergeRequestFileService.save(mergeRequestFile);

        storageService.upload(
                "requestsfiles",
                objectId + ".pdf",
                file.getInputStream(),
                file.getContentType()
        );
    }
}
