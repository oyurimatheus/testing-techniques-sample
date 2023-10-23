package me.oyurimatheus.testingtechniquessample.shopping;

import java.math.BigDecimal;
import java.util.Optional;

public enum DiscountType {

    MONETARY {

        @Override
        public BigDecimal apply(BigDecimal price, BigDecimal discount) {
            return price.subtract(discount);
        }

        @Override
        public Optional<BigDecimal> validateDiscountValue(BigDecimal discountValue) {
            if (discountValue.compareTo(BigDecimal.ZERO) <= 0) {
                return Optional.empty();
            }

            return Optional.of(discountValue);
        }

        @Override
        public BigDecimal effectiveValue(BigDecimal discountValue) {
            return discountValue;
        }
    },
    PERCENTAGE {

        @Override
        public BigDecimal apply(BigDecimal price, BigDecimal discount) {
            return price.multiply(discount);
        }

        @Override
        public Optional<BigDecimal> validateDiscountValue(BigDecimal discountValue) {
            if (discountValue.compareTo(BigDecimal.ZERO) <= 0
                    || discountValue.compareTo(new BigDecimal("1.0")) > 0) {
                return Optional.empty();
            }

            return Optional.of(discountValue);
        }

        @Override
        public BigDecimal effectiveValue(BigDecimal discountValue) {
            return new BigDecimal("1.0").subtract(discountValue);
        }
    };


    public abstract  BigDecimal  apply(BigDecimal price, BigDecimal discount);

    public abstract Optional<BigDecimal> validateDiscountValue(BigDecimal discountValue);

    public abstract BigDecimal effectiveValue(BigDecimal discountValue);
}
