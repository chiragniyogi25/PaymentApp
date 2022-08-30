package com.miniproject.payment.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transactions {

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(int openingBal) {
        this.openingBal = openingBal;
    }

    public int getClosingBal() {
        return closingBal;
    }

    public void setClosingBal(int closingBal) {
        this.closingBal = closingBal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RecurringPayments getRecc() {
        return recc;
    }

    public void setRecc(RecurringPayments recc) {
        this.recc = recc;
    }

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
