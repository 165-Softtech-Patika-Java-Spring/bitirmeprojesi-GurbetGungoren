package com.softtech.finalproject.model;

import com.softtech.finalproject.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
public class ProductCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProductTypeEnum productType;
    @Column(nullable = false)
    Double taxRates;
}