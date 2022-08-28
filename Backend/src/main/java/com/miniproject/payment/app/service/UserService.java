package com.miniproject.payment.app.service;

import com.miniproject.payment.app.dto.RecurringPaymentsDTO;
import com.miniproject.payment.app.dto.RecurringPaymentsQDO;
import com.miniproject.payment.app.dto.TransactionDTO;
import com.miniproject.payment.app.dto.UserDTO;
import com.miniproject.payment.app.entity.RecurringPayments;
import com.miniproject.payment.app.entity.Transactions;
import com.miniproject.payment.app.entity.User;
import com.miniproject.payment.app.repository.RecurringPaymentsRepository;
import com.miniproject.payment.app.repository.TransactionRepository;
import com.miniproject.payment.app.repository.UserRepository;
import org.hibernate.PropertyValueException;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EntityManager em;
    @Autowired
    private RecurringPaymentsRepository recurringPaymentsRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;
    @Transactional
    public String saveUser(UserDTO userdTo){
        User user = modelMapper.map(userdTo, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepository.save(user);
            return "success";
        }
        catch(PropertyValueException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Null Value not accepted");
        }
        catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        catch(Exception e){
            return e.toString();
        }
    }

    @Transactional
    public String saveUsers(List<UserDTO> lists){
        StringBuilder str=new StringBuilder();
        lists.stream()
                .forEach((curr_user)->{
                    User user=modelMapper.map(curr_user,User.class);
                    str.append(user.getEmail()+",");
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepository.save(user);
                });
        return str.toString().substring(0,str.toString().length()-1);
    }

    public List<UserDTO> getAllUsers(){
        List<UserDTO> users=new ArrayList<>();
        userRepository.findAll()
                .stream()
                .forEach((u)->{
                    UserDTO userdto=modelMapper.map(u,UserDTO.class);
                    users.add(userdto);
                });
        return users;
    }

    @Transactional
    public String addMoney(int id,int amount){
        User user=userRepository.findById(id).orElse(null);
        if(user==null)
            return "User not present";
        else{
            int initial_balance=user.getBalance();
            int closing_balance=initial_balance+amount;
            user.setBalance(closing_balance);
            Transactions transaction = new Transactions(
                    "CR", "Money Added", amount, initial_balance, closing_balance, user);
            transactionRepository.save(transaction);
//            userRepository.updateMoneyInUser(user.getId(), amount);
            return amount+" added";

        }
    }

    public String setupNewRecurringPayment(int id, RecurringPaymentsDTO recurringPaymentsDTO) {
        RecurringPayments recurringPayment = modelMapper.map(recurringPaymentsDTO, RecurringPayments.class);
        User user = userRepository.findById(id).get();
        recurringPayment.setUser(user);
        recurringPaymentsRepository.save(recurringPayment);
        try {
            return "success";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public List<RecurringPaymentsQDO> showRecurringPayments(int id) {
//        User user = userRepository.findById(id).get();
//        Query query = em.createNativeQuery("select * from recurring_payments where user_id=? and active=1", RecurringPayments.class);
//        query.setParameter(1, user);
//        List<RecurringPaymentsQDO> recurringPaymentsList = (List<RecurringPaymentsQDO>) query.getResultList();
//        return recurringPaymentsList;

        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<RecurringPayments> cq=cb.createQuery(RecurringPayments.class);

        Root<RecurringPayments> recurringPayments=cq.from(RecurringPayments.class);

//input->boolean
//        active=true;
        Predicate active=cb.equal(recurringPayments.get("active"),true);
        //recurring payment and user ko join
        //user_id= user_id=3
        Predicate userId=cb.equal(recurringPayments.join("user").get("id"),id);

        cq.select(recurringPayments).where(userId,active);

        TypedQuery<RecurringPayments> query=em.createQuery(cq);
        List<RecurringPayments> list=query.getResultList();
        List<RecurringPaymentsQDO> list1=new ArrayList<>();
        list.forEach(x->{
                    RecurringPaymentsQDO rr=modelMapper.map(x,RecurringPaymentsQDO.class);
                    list1.add(rr);
                });
        return list1;
    }

    public int getBalance(String email){
        User user=userRepository.findByEmail(email);
        return user.getBalance();
    }

    public List<Transactions> getStatement(int id, Date from, Date to){
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Transactions> cq=cb.createQuery(Transactions.class);

        Root<Transactions> root=cq.from(Transactions.class);

        Predicate date=cb.between(root.get("transactionDate"),from,to);
        Predicate userId=cb.equal(root.join("user").get("id"),id);
        cq.where(date,userId);

        TypedQuery<Transactions> query=em.createQuery(cq);
        return query.getResultList();
    }

//    @Transactional
    //delete from recurring_payments where recc_id=2 and user_id=3
    public String removeRecurringPayment(int id,int recc_id){
        if(recurringPaymentsRepository.deleteRecurringPaymentByUserIdAndReccId(recc_id,id)==0)
            return "No recurring Payment Found";
        else
            return "Recurring ID:"+recc_id+" deleted for UserId:"+id;
    }

    @Transactional
    public String transferMonthlyPayments(int userid){
        User user = userRepository.findById(userid).get();

        List<RecurringPayments> recurringPaymentsList= recurringPaymentsRepository
                .getRecurringPaymentsByUserIdAndStartDate(
                        userid,Calendar.getInstance().getTime());//new Timestamp(date.getTime()


        int flag=0;
        if(recurringPaymentsList.size()==0){
            return "No Pending Payments";
        }
        if(recurringPaymentsList.size()>0 && user.getBalance()==0)
            return "Insufficient Funds";
        List<String> list_of_desc=new ArrayList<>();
        recurringPaymentsList.stream().forEach(
                (recurringPayment) -> {
                    int openingBal = user.getBalance();
                    if (openingBal >= recurringPayment.getAmount() && recurringPayment.getNoOfTimes()>0) {
                        recurringPayment.setNoOfTimes(recurringPayment.getNoOfTimes() - 1);
                        if (recurringPayment.getNoOfTimes() == 0) {
                            recurringPayment.setActive(false);
                        }
                        user.setBalance(user.getBalance() - recurringPayment.getAmount());
                        int closingBal = user.getBalance();

                        Transactions transaction = new Transactions(
                                 "DB", recurringPayment.getDescription(),
                                recurringPayment.getAmount(), openingBal, closingBal, user);

                        userRepository.save(user);
                        recurringPaymentsRepository.save(recurringPayment);
                        transactionRepository.save(transaction);
                        list_of_desc.add(recurringPayment.getDescription());
                    }
                }
        );

        if(list_of_desc.size()==0){
            return "Sufficient Balance not available to pay";
        }
        StringBuilder str=new StringBuilder();
        for(int i=0;i<list_of_desc.size();i++){
            if(i==0 && i==list_of_desc.size()-1)
                str.append(list_of_desc.get(i)+ " is paid");
            else if(i==list_of_desc.size()-1){
                str.append(list_of_desc.get(i)+ " are paid");
            }else
                str.append(list_of_desc.get(i)+", ");
        }
        return str.toString();
    }
    //Criteria Query ->Getting error here
//    @Transactional
//    //delete from recurring_payments where recc_id=2 and user_id=3
//    public String removeRecurringPayment(int id,int recc_id){
//        CriteriaBuilder cb=em.getCriteriaBuilder();
//        CriteriaDelete<RecurringPayments> delete=cb.createCriteriaDelete(RecurringPayments.class);
//
//        Root<RecurringPayments> root=delete.from(RecurringPayments.class);
//
//        Subquery<RecurringPayments> subquery=delete.subquery(RecurringPayments.class);
//        Root<RecurringPayments> root2=subquery.from(RecurringPayments.class);
//        subquery.select(root2);
//
//        Join<RecurringPayments,User> join=root2.join("user");
//
//        Predicate reccId=cb.equal(root.get("reccId"),recc_id);
//
//        subquery.where(cb.equal(join.get("id"),id));
//        delete.where(reccId,root.in(subquery));
//        if(em.createQuery(delete).executeUpdate()==0)
//            return "No recurring Payment Found";
//        else
//            return "Recurring ID:"+recc_id+" deleted";
//    }
}
