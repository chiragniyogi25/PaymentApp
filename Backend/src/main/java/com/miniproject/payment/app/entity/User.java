package com.miniproject.payment.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="TBL_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true, length = 32)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    //since at the start user will have 0 balance
    private int balance = 0;
    private boolean isActive = false;
    private String roles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference//for parent
    private List<Transactions> transactionsList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<RecurringPayments> recurringPayments = new ArrayList<>();

    public void addTransaction(Transactions transaction) {
        this.transactionsList.add(transaction);
    }

    public void addRecurring(RecurringPayments recurringPayment) {
        this.recurringPayments.add(recurringPayment);
    }

    public User(int id, String name, String email, String password, int balance, boolean isActive, String roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.isActive = isActive;
        this.roles = roles;
    }
    public User(){

    }

}
