package com.gaejangmo.apiserver.model.image.user.domain;

import com.gaejangmo.apiserver.model.image.domain.UploadImage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class UserImage extends UploadImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
