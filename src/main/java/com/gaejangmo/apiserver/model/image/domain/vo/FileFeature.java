package com.gaejangmo.apiserver.model.image.domain.vo;

import com.gaejangmo.utils.StringUtils;
import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class FileFeature {
    static final int MAX_SIZE = 5_242_880;
    static final int MIN_SIZE = 1;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String savedName;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "image_type", nullable = false))
    private ImageType imageType;

    @Column(nullable = false)
    private long size;

    @Builder
    private FileFeature(final String url, final String originalName, final ImageType imageType, final long size, final String savedName) {
        validateUrl(url);
        validateOriginalName(originalName);
        validateSavedName(savedName);
        validateSize(size);
        this.url = url;
        this.originalName = originalName;
        this.savedName = savedName;
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

    private void validateSavedName(final String savedName) {
        if (StringUtils.isBlank(savedName)) {
            throw new IllegalArgumentException("잘못된 저장 파일 이름입니다.");
        }
    }

    private void validateSize(final long size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("파일의 size는 0~5 MB 사이만 가능합니다.");
        }
    }

    public String getImageType() {
        return imageType.value();
    }
}
