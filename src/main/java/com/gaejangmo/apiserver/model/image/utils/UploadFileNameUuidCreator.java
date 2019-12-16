package com.gaejangmo.apiserver.model.image.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UploadFileNameUuidCreator implements UploadFileNameCreator {

    @Override
    public String create(final String fileName) {
        return String.valueOf(UUID.randomUUID());
    }
}
