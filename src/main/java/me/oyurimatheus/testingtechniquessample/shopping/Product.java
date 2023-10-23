package me.oyurimatheus.testingtechniquessample.shopping;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Length(min = 3, max = 50)
    private String name;

    @Positive
    private BigDecimal price;

    @Length(min = 7, max = 7)
    private String sku;

    @Length(max = 500)
    @Lob
    private String description;

    public Product(String name,
                   BigDecimal price,
                   String sku,
                   String description) {
        this.name = name;
        this.price = price;
        this.sku = sku;
        this.description = description;
    }

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    Product() { }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
