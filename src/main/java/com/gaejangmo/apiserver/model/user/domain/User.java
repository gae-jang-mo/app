package com.gaejangmo.apiserver.model.user.domain;

import com.gaejangmo.apiserver.model.common.domain.BaseEntity;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.user.domain.vo.Email;
import com.gaejangmo.apiserver.model.user.domain.vo.Grade;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.domain.vo.Role;
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
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "email", nullable = false))
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

    @Builder
    public User(final String username, final Role role, final Grade grade,
                final Email email, final Motto motto, final Link imageUrl,
                final String introduce) {
        this.username = username;
        this.role = role;
        this.grade = grade;
        this.email = email;
        this.motto = motto;
        this.imageUrl = imageUrl;
        this.introduce = introduce;
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

    public User update(final String username, final String imageUrl) {
        this.username = username;
        this.imageUrl = Link.of(imageUrl);

        return this;
    }
}
