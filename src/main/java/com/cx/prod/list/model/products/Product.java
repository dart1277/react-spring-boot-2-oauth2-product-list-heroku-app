package com.cx.prod.list.model.products;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "PROD_ID")
    private Long id;
    @NotBlank
    @Column(name = "PROD_NAME", length = 100)
    private String prodName;
    @Column(name = "WEIGHT")
    private BigDecimal weight;
    @Column(name = "NET_PRICE")
    private BigDecimal netPrice;
    @Column(name = "GROSS_PRICE")
    private BigDecimal grossPrice;
}
