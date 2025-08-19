package com.power.power_pdf.dto;

import com.power.power_pdf.entity.MergeRequest;
import com.power.power_pdf.enums.MergeRequestStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class MergeRequestResponseDTO {

    private UUID id;
    private String name;
    private MergeRequestStatus status;
    private LocalDateTime createdAt;

    public MergeRequestResponseDTO() {}

    public MergeRequestResponseDTO(UUID id, String name, MergeRequestStatus status,
                                   LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
    }

    public MergeRequestResponseDTO fromEntity(MergeRequest mergeRequest) {
        return new MergeRequestResponseDTO(
                mergeRequest.getId(),
                mergeRequest.getFileName(),
                mergeRequest.getStatus(),
                mergeRequest.getCreatedAt()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MergeRequestStatus getStatus() {
        return status;
    }

    public  void setStatus(MergeRequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
