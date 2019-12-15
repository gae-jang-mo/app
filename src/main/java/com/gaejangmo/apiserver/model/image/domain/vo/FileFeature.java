package com.gaejangmo.apiserver.model.image.domain.vo;

import com.gaejangmo.utils.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Getter
@ToString
@EqualsAndHashCode
@Embeddable
public class FileFeature {

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String originalName;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "image_type", nullable = false))
    private ImageType imageType;

    @Column(nullable = false)
    private long size;

    @Builder
    private FileFeature(final String url, final String originalName, final ImageType imageType, final long size) {
        validateUrl(url);
        validateOriginalName(originalName);
        validateSize(size);
        this.url = url;
        this.originalName = originalName;
        this.imageType = imageType;
        this.size = size;
    }

    private void validateUrl(final String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url을 입력해주세요.");
        }
    }

    private void validateOriginalName(final String originalName) {
        if (StringUtils.isBlank(originalName)) {
            throw new IllegalArgumentException("잘못된 파일 이름입니다.");
        }
    }

    private void validateSize(final long size) {
        if (size <= 0 || size > 52_428_800) {
            throw new IllegalArgumentException("파일의 size는 0~50 MB 사이만 가능합니다.");
        }
    }

    public String getImageType() {
        return imageType.value();
    }
}
