package com.example.ecommerce.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderItem {
    private String ItemId;
    private int quantity;
}
