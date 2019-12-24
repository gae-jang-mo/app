package com.gaejangmo.apiserver.model.user.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserIntroduceDto {
    private String introduce;

    public UserIntroduceDto(final String introduce) {
        this.introduce = introduce;
    }
}
