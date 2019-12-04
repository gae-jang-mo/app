package com.gaejangmo.apiserver.model.userproduct.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class UserProductCreateDto {
    private String comment;
    private MultipartFile thumbnail;
    private int category;
    private long product_id;

    @Builder
    private UserProductCreateDto(final String comment, final MultipartFile thumbnail, final int category, final long product_id) {
        this.comment = comment;
        this.thumbnail = thumbnail;
        this.category = category;
        this.product_id = product_id;
    }
}
