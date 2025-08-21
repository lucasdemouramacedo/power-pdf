package com.power.power_pdf.repository;

import com.power.power_pdf.entity.MergeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MergeRequestRepository extends JpaRepository<MergeRequest, UUID> {

    List<MergeRequest> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    MergeRequest findFirstById(UUID id);
}
