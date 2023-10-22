package com.example.ecommerce.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequest {
    private String userId;
    private List<OrderItem> orderItems;
}
