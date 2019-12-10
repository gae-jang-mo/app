package com.gaejangmo.apiserver.model.userproduct.domain;

import com.gaejangmo.apiserver.model.common.BaseEntity;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.userproduct.domain.converter.ProductTypeAttributeConverter;
import com.gaejangmo.apiserver.model.userproduct.domain.exception.AlreadyDeleteException;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Where(clause = "deleted = 0")
public class UserProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    @Convert(converter = ProductTypeAttributeConverter.class)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_to_user_product"), updatable = false)
    private Product product;

    // TODO: 2019/12/10 User 관계 매핑

    @Builder
    public UserProduct(final String comment, final ProductType productType, final Product product) {
        this.comment = comment;
        this.productType = productType;
        this.product = product;
    }

    public boolean matchUser(final Long userId) {
        // TODO: 2019/12/11  user의 id랑 비교 user에 matchId() 넣기
        return true;
    }

    public boolean delete() {
        if (deleted) {
            throw new AlreadyDeleteException(this.id);
        }
        return deleted = true;
    }
}

