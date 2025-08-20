package com.power.power_pdf.service;

import com.power.power_pdf.entity.MergeRequestFile;
import com.power.power_pdf.repository.MergeRequestFileRepository;
import org.springframework.stereotype.Service;

@Service
public class MergeRequestFileService {

    private final MergeRequestFileRepository mergeRequestFileRepository;

    public MergeRequestFileService(MergeRequestFileRepository mergeRequestFileRepository) {
        this.mergeRequestFileRepository = mergeRequestFileRepository;
    }

    public MergeRequestFile save(MergeRequestFile mergeRequestFile) {
        try {
            return mergeRequestFileRepository.save(mergeRequestFile);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar merge request: " + e.getMessage());
        }
    }
}
