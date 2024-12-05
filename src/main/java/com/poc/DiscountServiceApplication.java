package com.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
@RefreshScope
@ComponentScan(basePackages = "com.poc")
public class DiscountServiceApplication {

    @Value("${discount.percentage.one}")
    private String discountPctOne;

    @Value("${discount.percentage.two}")
    private String discountPctTwo;

    Logger logger = LoggerFactory.getLogger(DiscountServiceApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(DiscountServiceApplication.class, args);
    }

    /**
     * Function bean to process products and apply discounts.
     * Input: List<Product>
     * Output: List<Product>
     */
    @Bean
    @Lazy
    public Function<List<Product>, List<Product>> addDiscountToProduct() {
        return products -> {
            List<Product> productList = new ArrayList<>();
            for (Product product : products) {
                if (product.getPrice() > 8000) {
                    logger.debug("Product price is greater than 8000");
                    logger.debug("Discount percentage [one] is {}", discountPctOne);
                    productList.add(calculatePrice(product, Math.toIntExact(Long.parseLong(discountPctOne))));
                } else if (product.getPrice() > 5000) {
                    logger.debug("Product price is greater than 5000");
                    logger.debug("Discount percentage [two] is {}", discountPctTwo);
                    productList.add(calculatePrice(product, Math.toIntExact(Long.parseLong(discountPctTwo))));
                }
            }
            return productList;
        };
    }

    private Product calculatePrice(Product product, int percentage) {
        double actualPrice = product.getPrice();
        double discount = actualPrice * percentage / 100;
        product.setPrice(actualPrice - discount);
        logger.info("Product actual price is {}, after discount total price is {}",
                Optional.of(actualPrice), Optional.of(product.getPrice()));
        return product;
    }
}
