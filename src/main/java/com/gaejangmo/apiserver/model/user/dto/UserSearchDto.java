package com.gaejangmo.apiserver.model.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSearchDto {
    private Long id;
    private String imageUrl;
    private String username;
    private String motto;
    private Boolean isLiked;
    private Boolean isCelebrity;

    @Builder
    public UserSearchDto(final Long id, final String imageUrl, final String username, final String motto,
                         final Boolean isLiked, final Boolean isCelebrity) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.username = username;
        this.motto = motto;
        this.isLiked = isLiked;
        this.isCelebrity = isCelebrity;
    }
}
