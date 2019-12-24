package com.gaejangmo.apiserver.model.userproduct.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class CommentDto {
    private String comment;

    public CommentDto(final String comment) {
        this.comment = comment;
    }
}
