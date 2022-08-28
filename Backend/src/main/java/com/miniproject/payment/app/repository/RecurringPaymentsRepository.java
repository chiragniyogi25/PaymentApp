package com.miniproject.payment.app.repository;

import com.miniproject.payment.app.dto.RecurringPaymentsQDO;
import com.miniproject.payment.app.entity.RecurringPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface RecurringPaymentsRepository extends JpaRepository<RecurringPayments, Integer> {
    @Modifying
    @Transactional
    @Query("delete from RecurringPayments r where r.reccId=:rec_id and r.user.id=:userId")
    int deleteRecurringPaymentByUserIdAndReccId(@Param("rec_id") int reccId,@Param("userId") int user_id);

//    @Query("select r.reccId,r.description,r.amount,r.startDate,r.noOfTimes,r.active,r.user.id from RecurringPayments r where (r.user.id=:userId AND r.startDate<=:currDate) AND (active=1 AND noOfTimes>0)")
    @Query(
            value="select r.recc_id,r.description,r.amount,r.start_date,r.no_of_times,r.active,r.user_id from recurring_payments r where (r.user_id=?1 AND r.start_date<=?2) AND (r.active=1 AND r.no_of_times>0)",
        nativeQuery=true
    )
    List<RecurringPayments> getRecurringPaymentsByUserIdAndStartDate( int user_id, Date curr_date);
}
