package com.example.order.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    @Id
    private int id;
    private int itemId;
    private String orderDate;
    private int amount;
}
