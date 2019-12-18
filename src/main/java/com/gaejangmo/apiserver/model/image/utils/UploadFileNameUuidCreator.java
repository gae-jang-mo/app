package com.gaejangmo.apiserver.model.image.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UploadFileNameUuidCreator implements UploadFileNameCreator {

    @Override
    public String create(final String fileName) {
        String extension = extractExtension(fileName);
        return String.format("%s.%s", UUID.randomUUID(), extension);
    }

    private String extractExtension(final String fileName) {
        int i = fileName.indexOf('.');
        return fileName.substring(i + 1);
    }
}
