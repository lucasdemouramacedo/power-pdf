package com.power.power_pdf.repository;

import com.power.power_pdf.dto.MergeRequestCountByDateDTO;
import com.power.power_pdf.entity.MergeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReportMergeRequestRepository extends JpaRepository<MergeRequest, UUID> {

    @Query("""
        SELECT new com.power.power_pdf.dto.MergeRequestCountByDateDTO(
            CAST(m.createdAt AS LocalDate),
            COUNT(m.id)
        )
        FROM MergeRequest m
        WHERE m.createdAt BETWEEN :start AND :end
        GROUP BY CAST(m.createdAt AS LocalDate)
        ORDER BY CAST(m.createdAt AS LocalDate) ASC
    """)
    List<MergeRequestCountByDateDTO> findMergeRequestCountByDate(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
