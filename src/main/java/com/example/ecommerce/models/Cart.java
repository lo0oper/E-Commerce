package com.example.ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@Getter
public class Cart {
    private String cartId;
    private List<Item> itemList;
    private String userID;
    private List<Discount> discounts;
    private int totalCost;

    public Cart() {

    }
}
