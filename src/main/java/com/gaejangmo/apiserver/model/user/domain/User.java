package com.gaejangmo.apiserver.model.user.domain;

import com.gaejangmo.apiserver.model.common.domain.BaseTimeEntity;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.image.user.domain.UserImage;
import com.gaejangmo.apiserver.model.user.domain.converter.GradeAttributeConverter;
import com.gaejangmo.apiserver.model.user.domain.converter.RoleAttributeConverter;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Grade;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.domain.vo.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long oauthId;

    @Column(unique = true, nullable = false)
    private String username;

    @Convert(converter = RoleAttributeConverter.class)
    @Column(nullable = false)
    private Role role;

    @Convert(converter = GradeAttributeConverter.class)
    @Column(nullable = false)
    private Grade grade;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "email"))
    private Email email;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_image_id", foreignKey = @ForeignKey(name = "fk_user_image_to_user"))
    private UserImage userImage;

    @Builder
    public User(final Long oauthId, final String username, final Role role, final Grade grade, final Email email, final Motto motto, final Link imageUrl, final String introduce) {
        this.oauthId = oauthId;
        this.username = username;
        this.role = role;
        this.grade = grade;
        this.email = email;
        this.motto = motto;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
    }

    public boolean matchId(final Long id) {
        return this.id.equals(id);
    }

    public String getGrade() {
        return grade.getTitle();
    }

    public String getRoleType() {
        return role.getType();
    }

    public String getRoleTitle() {
        return role.getTitle();
    }

    public String getEmail() {
        return email.value();
    }

    public String getMotto() {
        return motto.value();
    }

    public String getImageUrl() {
        return imageUrl.value();
    }

    public Optional<UserImage> getUserImage() {
        return Optional.ofNullable(userImage);
    }

    public User update(final String username, final String imageUrl) {
        this.username = username;
        this.imageUrl = Link.of(imageUrl);
        return this;
    }

    public User updateUserImage(final UserImage userImage) {
        this.userImage = userImage;
        return this;
    }
}
