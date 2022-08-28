package com.miniproject.payment.app.dto;
import lombok.Data;

import java.util.Date;

@Data
//for output
public class RecurringPaymentsQDO {
    private int reccId;
    private String description;
    private int amount;
    private Date startDate;
    private int noOfTimes;

    public RecurringPaymentsQDO() {
    }

    public RecurringPaymentsQDO(int reccId, String description, int amount, Date startDate, int noOfTimes) {
        this.reccId = reccId;
        this.description = description;
        this.amount = amount;
        this.startDate = startDate;
        this.noOfTimes = noOfTimes;
    }
}
