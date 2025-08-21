package com.power.power_pdf.repository;

import com.power.power_pdf.entity.MergeRequestFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MergeRequestFileRepository extends JpaRepository<MergeRequestFile, UUID> {

    List<MergeRequestFile> findMergeRequestFilesByMergeRequestIdOrderByCreatedAtAsc(UUID mergeRequestId);
}
