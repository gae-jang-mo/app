package com.gaejangmo.apiserver.model.like.domain;

import com.gaejangmo.apiserver.model.common.domain.BaseTimeEntity;
import com.gaejangmo.apiserver.model.user.domain.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_source_id", foreignKey = @ForeignKey(name = "fk_user_to_source_likes"))
    private User source;

    @ManyToOne
    @JoinColumn(name = "user_target_id", foreignKey = @ForeignKey(name = "fk_user_to_likes_target"))
    private User target;

    @Builder
    public Likes(final User source, final User target) {
        this.source = source;
        this.target = target;
    }
}
