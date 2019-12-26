package com.gaejangmo.apiserver.model.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
