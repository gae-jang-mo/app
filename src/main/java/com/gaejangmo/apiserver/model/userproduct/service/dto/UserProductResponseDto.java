package com.gaejangmo.apiserver.model.userproduct.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserProductResponseDto {
    private long id;
    private int category;

    @Builder
    private UserProductResponseDto(final long id, final int category) {
        this.id = id;
        this.category = category;
    }

    // TODO: 2019/12/05 Product 정보

    // TODO: 2019/12/05 이미지 url
}