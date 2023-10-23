package me.oyurimatheus.testingtechniquessample.shopping;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;
import static me.oyurimatheus.testingtechniquessample.shopping.DiscountCoupon.Status.ACTIVE;

@Entity
@Table(name = "discount_coupons")
public class DiscountCoupon {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Length(min = 5)
    private String name;

    @Length(min = 5)
    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @Positive
    private BigDecimal discountValue;

    @NotNull
    private UUID campaignId;


    @Enumerated(EnumType.STRING)
    private Status status = ACTIVE;


    public DiscountCoupon(String name,
                          String code,
                          DiscountType type,
                          BigDecimal discountValue,
                          UUID campaignId) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.discountValue = type.validateDiscountValue(discountValue)
                                 .orElseThrow(() ->
                                         new IllegalArgumentException(String.format("discount value %s is invalid for type %s", discountValue, type)));
        this.campaignId = campaignId;
    }

    public DiscountCoupon(String name,
                          String code,
                          DiscountType type,
                          BigDecimal discountValue,
                          UUID campaignId,
                          Status status) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.discountValue = discountValue;
        this.campaignId = campaignId;
        this.status = status;
    }

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    DiscountCoupon() { }

    public BigDecimal apply(BigDecimal subtotal) {
        return null;
    }

    public boolean isActive() {
        return status == ACTIVE;
    }


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public DiscountType getType() {
        return type;
    }

    public BigDecimal getDiscountValue() {
        return type.effectiveValue(discountValue);
    }

    public enum Status {
        ACTIVE, INACTIVE;
    }
}
