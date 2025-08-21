package com.power.power_pdf.dto;

import com.power.power_pdf.entity.MergeRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public class MergeRequestResponseDTO {

    private UUID id;
    private String name;
    private String link;
    private LocalDateTime createdAt;

    public MergeRequestResponseDTO() {}

    public MergeRequestResponseDTO(UUID id, String name, String link,
                                   LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.createdAt = createdAt;
    }

    public MergeRequestResponseDTO fromEntity(MergeRequest mergeRequest) {
        return new MergeRequestResponseDTO(
                mergeRequest.getId(),
                mergeRequest.getFileName(),
                mergeRequest.getLink(),
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

    public String getLink() {
        return link;
    }

    public  void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
