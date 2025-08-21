package com.power.power_pdf.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class MergerService {

    public byte[] mergePdfs(List<InputStream> files) throws IOException {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();

        for (InputStream file : files) {
            if (file != null) {
                pdfMerger.addSource(file);
            }
        }
        try (ByteArrayOutputStream mergedPdfOutputStream = new ByteArrayOutputStream()) {
            pdfMerger.setDestinationStream(mergedPdfOutputStream);

            pdfMerger.mergeDocuments(null);

            return mergedPdfOutputStream.toByteArray();
        }
    }
}
