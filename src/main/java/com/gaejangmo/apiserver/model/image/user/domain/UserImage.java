package com.gaejangmo.apiserver.model.image.user.domain;

import com.gaejangmo.apiserver.model.image.domain.UploadImage;
import com.gaejangmo.apiserver.model.image.domain.vo.FileFeature;
import com.gaejangmo.apiserver.model.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserImage extends UploadImage {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_to_user_image"), updatable = false, nullable = false)
    private User user;

    @Builder
    public UserImage(final FileFeature fileFeature, final User user) {
        super(fileFeature);
        this.user = user;
    }
}
