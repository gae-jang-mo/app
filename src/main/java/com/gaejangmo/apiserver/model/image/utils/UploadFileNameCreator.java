package com.gaejangmo.apiserver.model.image.utils;

@FunctionalInterface
public interface UploadFileNameCreator {
    String create(final String fileName);
}
