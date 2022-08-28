package com.miniproject.payment.app.dto;

import com.miniproject.payment.app.entity.User;
import lombok.AllArgsConstructor;

import java.util.Calendar;
import java.util.Date;

public class TransactionDTO {
    private int transactionId;
    private String type;
    private Date transactionDate= Calendar.getInstance().getTime();
    private String description;
    private int amount;
    private int openingBal;
    private int closingBal;
    private int userId;
    public TransactionDTO(String type,
                        String description,
                        int amount,
                        int openingBal,
                        int closingBal,
                        int userId) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.openingBal = openingBal;
        this.closingBal = closingBal;
        this.userId = userId;
    }
}
