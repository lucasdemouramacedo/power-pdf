package com.power.power_pdf.service;

import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.exceptions.MergeRequestCreationException;
import com.power.power_pdf.repository.MergeRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class MergeRequestService {

    private final MergeRequestRepository mergeRequestRepository;

    public MergeRequestService(MergeRequestRepository mergeRequestRepository) {
        this.mergeRequestRepository = mergeRequestRepository;
    }

    public MergeRequest save(MergeRequest mergeRequest) {
        try {
            return mergeRequestRepository.save(mergeRequest);
        } catch (Exception e) {
            throw new MergeRequestCreationException();
        }
    }
}
