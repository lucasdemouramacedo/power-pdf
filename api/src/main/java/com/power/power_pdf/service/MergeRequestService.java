package com.power.power_pdf.service;

import com.power.power_pdf.dto.FileDownloadDTO;
import com.power.power_pdf.dto.MergeRequestResponseDTO;
import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.entity.MergeRequestFile;
import com.power.power_pdf.enums.MergeRequestStatus;
import com.power.power_pdf.exceptions.MergeRequestCreationException;
import com.power.power_pdf.messaging.producer.MergerProducer;
import com.power.power_pdf.repository.MergeRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;
    private final MergeRequestFileService mergeRequestFileService;
    private final StorageService storageService;
    private final MergerProducer mergerProducer;
    private final MergerService mergerService;

    public MergeRequestService(MergeRequestRepository mergeRequestRepository,
                               MergeRequestFileService mergeRequestFileService,
                               StorageService storageService,
                               MergerProducer mergerProducer,
                               MergerService mergerService) {
        this.mergeRequestRepository = mergeRequestRepository;
        this.mergeRequestFileService = mergeRequestFileService;
        this.storageService = storageService;
        this.mergerProducer = mergerProducer;
        this.mergerService = mergerService;
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
            MergeRequest mergeRequestSaved = this.save(mergeRequest);

            for (MultipartFile file : files) {
                uploadFile(file, mergeRequestSaved);
            }

            mergerProducer.sendMessage(mergeRequestSaved.getId().toString());
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
    
    public MergeRequest getMergeRequestById(UUID id){
        return mergeRequestRepository.findFirstById(id);
    }

    @Transactional
    public void makePdfsMerge(String mergeRequestId) throws IOException {
        MergeRequest mergeRequest = this.getMergeRequestById(UUID.fromString(mergeRequestId));
        List<MergeRequestFile> mergeRequestFiles = mergeRequestFileService.getFilesByMergeRequestId(mergeRequest.getId());

        List<InputStream> files = downloadFiles(mergeRequestFiles);
        byte[] mergedPdfBytes = mergerService.mergePdfs(files);
        String objectId = UUID.randomUUID().toString() + "_" + mergeRequest.getFileName() + ".pdf";
        try {
            storageService.upload(
                    "mergedfiles",
                    objectId,
                    new ByteArrayInputStream(mergedPdfBytes),
                    "application/pdf"
            );

            mergeRequest.setObjectId(objectId);
            mergeRequest.setMergedAt(LocalDateTime.now());
            mergeRequest.setStatus(MergeRequestStatus.COMPLETED);
            this.save(mergeRequest);
        } finally {
            for (InputStream file : files) {
                if (file != null) {
                    file.close();
                }
            }
        }
    }

    private void uploadFile(MultipartFile file, MergeRequest mergeRequest) throws IOException {
        String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String objectId = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        MergeRequestFile mergeRequestFile = new MergeRequestFile(mergeRequest, baseName, objectId);
        mergeRequestFileService.save(mergeRequestFile);

        storageService.upload(
                "requestsfiles",
                objectId,
                file.getInputStream(),
                file.getContentType()
        );
    }

    public List<InputStream> downloadFiles(List<MergeRequestFile> mergeRequestFiles) throws IOException {
        List<InputStream> files = new ArrayList<>();
        for (MergeRequestFile mergeRequestFile : mergeRequestFiles) {
            files.add(storageService.download("requestsfiles", mergeRequestFile.getObjectId()));
        }
        return files;
    }

    public FileDownloadDTO downloadFile(String mergeRequestId) {
        MergeRequest mergeRequest = this.getMergeRequestById(UUID.fromString(mergeRequestId));
        InputStream file = storageService.download("mergedfiles", mergeRequest.getObjectId());
        InputStreamResource resource = new InputStreamResource(file);
        String filename = mergeRequest.getFileName() + ".pdf";

        return new FileDownloadDTO(resource, filename, MediaType.APPLICATION_PDF);
    }
}
