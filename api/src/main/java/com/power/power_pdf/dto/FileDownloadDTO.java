package com.power.power_pdf.dto;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

public class FileDownloadDTO {
    private final InputStreamResource resource;
    private final String filename;
    private final MediaType mediaType;

    public FileDownloadDTO(InputStreamResource resource, String filename, MediaType mediaType) {
        this.resource = resource;
        this.filename = filename;
        this.mediaType = mediaType;
    }

    public InputStreamResource getResource() {
        return resource;
    }

    public String getFilename() {
        return filename;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}