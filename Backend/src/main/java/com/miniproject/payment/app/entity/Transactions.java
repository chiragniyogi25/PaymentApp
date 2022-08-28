package com.miniproject.payment.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private String type;
    private Date transactionDate= Calendar.getInstance().getTime();//current date
    private String description;
    private int amount;
    private int openingBal;
    private int closingBal;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference//for child
    private User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recc_id")
    @JsonBackReference
    private RecurringPayments recc;

    public Transactions(String type,
                        String description,
                        int amount,
                        int openingBal,
                        int closingBal,
                        User user) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.openingBal = openingBal;
        this.closingBal = closingBal;
        this.user = user;
    }
}
