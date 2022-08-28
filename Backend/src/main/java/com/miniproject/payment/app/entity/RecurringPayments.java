package com.miniproject.payment.app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
public class RecurringPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reccId;
    private String description;
    private int amount;
    private Date startDate;
    private int noOfTimes;
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;//(id,name,email,)

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recc")
    @JsonManagedReference
    private List<Transactions> transactions = new ArrayList<>();

    public RecurringPayments(int reccId, User user, String description, int amount, Date startDate, int noOfTimes, boolean active) {
        this.reccId = reccId;
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.noOfTimes = noOfTimes;
        this.active = active;
    }

    public int getReccId() {
        return reccId;
    }

    public void setReccId(int reccId) {
        this.reccId = reccId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNoOfTimes() {
        return noOfTimes;
    }

    public void setNoOfTimes(int noOfTimes) {
        this.noOfTimes = noOfTimes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transactions transaction) {
        this.transactions.add(transaction);
    }

    public RecurringPayments(){

    }

    public RecurringPayments(User user, String description, int amount, int noOfTimes){
        this.user = user;
        this.description = description;
        this.amount = amount;
        this.noOfTimes = noOfTimes;
        this.active = true;
    }
}
