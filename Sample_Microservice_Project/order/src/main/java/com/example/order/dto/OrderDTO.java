package com.example.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private int itemId;
    private String orderDate;
    private int amount;

    public int getItemId() {   // to get item id
        return itemId;
    }
}
