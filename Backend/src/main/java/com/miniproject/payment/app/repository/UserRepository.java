package com.miniproject.payment.app.repository;

import com.miniproject.payment.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
//    Optional<User> findByEmail1(String email);
//    @Modifying
//    @Transactional
//    @Query(
//            name="UPDATE tbl_user t SET t.balance=?2 WHERE t.id=?1",
//            nativeQuery = true
//    )
//    int updateMoneyInUser(int id,int amount);
}
