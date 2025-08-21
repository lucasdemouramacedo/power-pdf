package com.power.power_pdf.entity;

import com.power.power_pdf.enums.MergeRequestStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "merge_requests")
public class MergeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    @Column(name = "object_id", nullable = true)
    private String objectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MergeRequestStatus status = MergeRequestStatus.PENDING;

    @Column(name = "merged_at")
    private LocalDateTime mergedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public MergeRequest() {}

    public MergeRequest(String fileName) {
        this.fileName = fileName;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public MergeRequestStatus getStatus() {
        return this.status;
    }

    public void setStatus(MergeRequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getMergedAt() {
        return this.mergedAt;
    }

    public void setMergedAt(LocalDateTime mergedAt) {
        this.mergedAt = mergedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getLink() {
        return "http://localhost:8080/merge/" + this.id + "/download";
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
