package com.example.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Discount {
    private String discountId;
    private int discountAmount;
    private String cartId;
    private String userId;
    private String discountCode;
}
