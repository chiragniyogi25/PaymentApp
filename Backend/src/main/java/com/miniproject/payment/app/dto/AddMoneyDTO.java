package com.miniproject.payment.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//DTO For User to update its amount
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddMoneyDTO {
    private int amount;
}
