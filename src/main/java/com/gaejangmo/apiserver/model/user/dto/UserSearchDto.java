package com.gaejangmo.apiserver.model.user.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
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
