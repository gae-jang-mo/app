package com.gaejangmo.apiserver.model.notice.dto;

import com.gaejangmo.apiserver.model.notice.domain.NoticeType;
import com.gaejangmo.apiserver.model.product.dto.validator.EnumValue;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class NoticeRequestDto {
    @EnumValue(enumClass = NoticeType.class)
    private String noticeType;

    @NotBlank
    private String header;

    @NotBlank
    private String contents;

    @Builder
    public NoticeRequestDto(final String noticeType, @NotBlank final String header, @NotBlank final String contents) {
        this.noticeType = noticeType;
        this.header = header;
        this.contents = contents;
    }
}
