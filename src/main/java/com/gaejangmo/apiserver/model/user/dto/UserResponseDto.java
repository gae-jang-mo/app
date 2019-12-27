package com.gaejangmo.apiserver.model.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private Long oauthId;
    private String username;
    private String email;
    private String motto;
    private String imageUrl;
    private String introduce;
    private Boolean isLiked;
    private Boolean isCelebrity;
    private Integer totalLike;

    @Builder
    public UserResponseDto(final Long id, final Long oauthId, final String username, final String email, final String motto,
                           final String imageUrl, final String introduce, final Boolean isLiked, final Boolean isCelebrity, final Integer totalLike) {
        this.id = id;
        this.oauthId = oauthId;
        this.username = username;
        this.email = email;
        this.motto = motto;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
        this.isLiked = isLiked;
        this.isCelebrity = isCelebrity;
        this.totalLike = totalLike;
    }
}

