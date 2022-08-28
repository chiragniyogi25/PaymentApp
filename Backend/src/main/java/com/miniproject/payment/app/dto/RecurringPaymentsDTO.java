package com.miniproject.payment.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RecurringPaymentsDTO {
    private String description;
    private int amount;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date startDate;
    private int noOfTimes;
}
