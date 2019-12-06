package com.gaejangmo.apiserver.model.user.domain;

import com.gaejangmo.apiserver.model.common.domain.BaseEntity;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long githubId;

    @Column(unique = true, nullable = false)
    private String username;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "motto"))
    private Motto motto;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "imageUrl", nullable = false))
    private Link imageUrl;

    @Lob
    @Column
    private String introduce;

    @Builder
    public User(final Long githubId, final String username, final Motto motto,
                final Link imageUrl, final String introduce) {
        this.githubId = githubId;
        this.username = username;
        this.motto = motto;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
    }

    public String getMotto() {
        return motto.value();
    }

    public String getImageUrl() {
        return imageUrl.value();
    }
}
