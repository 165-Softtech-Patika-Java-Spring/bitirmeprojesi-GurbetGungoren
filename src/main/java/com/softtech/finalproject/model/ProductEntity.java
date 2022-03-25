package com.softtech.finalproject.model;

import com.softtech.finalproject.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="PRODUCT")
public class ProductEntity extends BaseEntity{
    @Id
    @SequenceGenerator(name = "Product" , sequenceName = "PRODUCT_ID_SEQ")
    @GeneratedValue(generator = "Product")
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false,precision =19 ,scale = 2)
    private BigDecimal productPrice;

    @Column(nullable = false,precision =19 ,scale = 2)
    private BigDecimal taxFreeSellingPrice;

    @ManyToOne
    private ProductCategory productCategory;
}
