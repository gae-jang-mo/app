package com.gaejangmo.apiserver.model.image.domain;


import com.gaejangmo.apiserver.model.common.domain.BaseTimeEntity;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class UploadImage extends BaseTimeEntity {

    @Embedded
    private FileFeature fileFeature;

    protected UploadImage(final FileFeature fileFeature) {
        this.fileFeature = fileFeature;
    }
}

