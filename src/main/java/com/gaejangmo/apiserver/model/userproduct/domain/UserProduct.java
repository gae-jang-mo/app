package com.gaejangmo.apiserver.model.userproduct.domain;


import com.gaejangmo.apiserver.model.common.domain.BaseEntity;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.userproduct.domain.converter.ProductTypeAttributeConverter;
import com.gaejangmo.apiserver.model.userproduct.domain.exception.AlreadyDeleteException;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Where(clause = "deleted = 0")
public class UserProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "comment", nullable = false))
    private Comment comment;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @Convert(converter = ProductTypeAttributeConverter.class)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_to_user_product"), updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_to_user_product"), updatable = false)
    private User user;

    @Builder
    public UserProduct(final Comment comment, final ProductType productType, final Product product, final User user) {
        this.comment = comment;
        this.productType = productType;
        this.product = product;
        this.user = user;
    }

    public boolean matchUser(final Long userId) {
        return user.matchId(userId);
    }

    public UserProduct changeComment(final Comment comment) {
        this.comment = comment;
        return this;
    }

    public UserProduct changeProductType(final ProductType productType) {
        this.productType = productType;
        return this;
    }

    public boolean delete() {
        if (deleted) {
            throw new AlreadyDeleteException(this.id);
        }
        return deleted = true;
    }

    public String getComment() {
        return comment.value();
    }
}

