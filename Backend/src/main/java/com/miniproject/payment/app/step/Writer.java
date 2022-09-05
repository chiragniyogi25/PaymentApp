package com.miniproject.payment.app.step;

import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.repository.RecurringPaymentsRepository;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
;
import java.util.List;
@NoArgsConstructor
public class Writer implements ItemWriter<RecurringPayments> {
    RecurringPaymentsRepository recurringPaymentsRepository;

    public Writer(RecurringPaymentsRepository recurringPaymentsRepository){
        this.recurringPaymentsRepository=recurringPaymentsRepository;
    }

    @Override
    public void write(List<? extends RecurringPayments> list) throws Exception {
        for(RecurringPayments r:list){
            recurringPaymentsRepository.save(r);
        }
    }
}
