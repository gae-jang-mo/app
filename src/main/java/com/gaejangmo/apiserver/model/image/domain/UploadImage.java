package com.gaejangmo.apiserver.model.image.domain;


import com.gaejangmo.apiserver.model.common.domain.BaseTimeEntity;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class UploadImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FileFeature fileFeature;

    protected UploadImage(final FileFeature fileFeature) {
        this.fileFeature = fileFeature;
    }
}

