package com.miniproject.payment.app.step;

import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.jpaAuth.CustomUserDetail;
import com.miniproject.payment.app.repository.RecurringPaymentsRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.List;


public class Reader implements ItemReader<RecurringPayments> {
    private RecurringPaymentsRepository recurringPaymentsRepository;
    public List<RecurringPayments> recurringPaymentsList;
    public Reader(){}

    public Reader(RecurringPaymentsRepository recurringPaymentsRepository){
        this.recurringPaymentsRepository=recurringPaymentsRepository;
        recurringPaymentsList=recurringPaymentsRepository.findAll();
    }

    private int count=0;

    @Override
    public RecurringPayments read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(count<recurringPaymentsList.size()) {
            return recurringPaymentsList.get(count++);
        }else{
            count=0;
        }
        return null;
    }
}
