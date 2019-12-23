package com.gaejangmo.apiserver.model.user.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSearchDto {
    private Long id;
    private String imageUrl;
    private String username;

    @Builder
    public UserSearchDto(final Long id, final String imageUrl, final String username) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.username = username;
    }
}
