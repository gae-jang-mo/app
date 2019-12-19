package com.gaejangmo.apiserver.model.image.dto;

import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class FileResponseDto {
    private Long id;
    private FileFeature fileFeature;
    private LocalDateTime createdAt;

    @Builder
    public FileResponseDto(final Long id, final FileFeature fileFeature, final LocalDateTime createdAt) {
        this.id = id;
        this.fileFeature = fileFeature;
        this.createdAt = createdAt;
    }
}
