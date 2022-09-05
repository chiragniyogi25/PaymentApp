package com.miniproject.payment.app.step;

import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.entity.Transactions;
import com.miniproject.payment.app.entity.User;
import com.miniproject.payment.app.repository.RecurringPaymentsRepository;
import com.miniproject.payment.app.repository.TransactionRepository;
import com.miniproject.payment.app.repository.UserRepository;
import com.miniproject.payment.app.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
@NoArgsConstructor
public class Process implements ItemProcessor<RecurringPayments,RecurringPayments> {

    private UserService userService;
    private RecurringPaymentsRepository recurringPaymentsRepository;
    private TransactionRepository transactionRepository;
    public Process(UserService userService,RecurringPaymentsRepository recurringPaymentsRepository,TransactionRepository transactionRepository){
        this.userService=userService;
        this.recurringPaymentsRepository=recurringPaymentsRepository;
        this.transactionRepository=transactionRepository;
    }
    @Override
    public RecurringPayments process(RecurringPayments recurringPayments) throws Exception {
        int userId=recurringPayments.getUser().getId();
        User user = userService.getUser(userId);
        if(recurringPayments.getActive()
                &&
                user.getBalance()>=recurringPayments.getAmount()
                &&
                (new Date()).after(recurringPayments.getStartDate()) ){
            Transactions transaction = new Transactions(
                    "DB", recurringPayments.getDescription(),
                    recurringPayments.getAmount(), user.getBalance(), user.getBalance()-recurringPayments.getAmount(), user);
            transactionRepository.save(transaction);
            user.setBalance(user.getBalance()-recurringPayments.getAmount());
            userService.updateUser(user);
            recurringPayments.setNoOfTimes(recurringPayments.getNoOfTimes()-1);

            Date date=recurringPayments.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date); // don't forget this if date is arbitrary e.g. 01-01-2014
            int month = cal.get(Calendar.MONTH);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            if(month<12)
                month++;
            if(month==12){
                month=1;
                year++;
            }
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            date=cal.getTime();

            recurringPayments.setStartDate(date);
            if(recurringPayments.getNoOfTimes()==0)
                recurringPayments.setActive(false);
        }
        return recurringPayments;
    }
}
