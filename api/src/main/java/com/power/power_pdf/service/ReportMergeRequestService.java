package com.power.power_pdf.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.power.power_pdf.dto.MergeRequestCountByDateDTO;
import com.power.power_pdf.repository.ReportMergeRequestRepository;

@Service
public class ReportMergeRequestService {

    private final ReportMergeRequestRepository reportMergeRequestRepository;

    public ReportMergeRequestService(
            ReportMergeRequestRepository reportMergeRequestRepository) {
        this.reportMergeRequestRepository = reportMergeRequestRepository;
    }

    public List<MergeRequestCountByDateDTO> reportMergeRequestsByDate(LocalDate start, LocalDate end) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        List<MergeRequestCountByDateDTO> existingCounts = reportMergeRequestRepository
                .findMergeRequestCountByDate(startDateTime, endDateTime);

        Map<LocalDate, Long> countsByDate = existingCounts.stream()
                .collect(Collectors.toMap(
                        MergeRequestCountByDateDTO::getDate,
                        MergeRequestCountByDateDTO::getMergeRequestCount));

        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> {
                    long count = countsByDate.getOrDefault(date, 0L);
                    return new MergeRequestCountByDateDTO(date, count);
                })
                .collect(Collectors.toList());
    }
}
