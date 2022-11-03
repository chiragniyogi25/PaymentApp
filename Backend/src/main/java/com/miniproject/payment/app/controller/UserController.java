package com.miniproject.payment.app.controller;

import com.miniproject.payment.app.dto.*;
import com.miniproject.payment.app.entity.AuthenticationRequestEntity;
import com.miniproject.payment.app.entity.AuthenticationResponse;
import com.miniproject.payment.app.entity.Transactions;
import com.miniproject.payment.app.jpaAuth.CustomUserDetail;
import com.miniproject.payment.app.jpaAuth.MyUserDetailsService;
import com.miniproject.payment.app.service.UserService;
import com.miniproject.payment.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;
//http://lo
    @PostMapping("/signup")
    public String addUser(@RequestBody UserDTO userDto){
        return userService.saveUser(userDto);
    }
    @PostMapping("/signups")
    public String addUsers(@RequestBody List<UserDTO> userDTOs){
        return userService.saveUsers(userDTOs);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestEntity authenticationRequestEntity) throws Exception {
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestEntity.getEmail(),
                        authenticationRequestEntity.getPassword())
        );}
        catch (Exception e){
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails=userDetailsService
                .loadUserByUsername(authenticationRequestEntity.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/home")
    public String homePage(@RequestHeader(name="Authorization") String token){
        return "Hi! "+jwtTokenUtil.extractUsername(token.substring(7));
    }

    @PutMapping("/addMoney")
    public String addMoney(Authentication authentication, @RequestBody AddMoneyDTO addMoneyDTO){
        int amt=addMoneyDTO.getAmount();
        CustomUserDetail customUserDetail=(CustomUserDetail) authentication.getPrincipal();
        return userService.addMoney(customUserDetail.getId(),amt);
    }

    @PostMapping("/recurring_payment")
    public String setupNewRecurringPayment(Authentication authentication, @RequestBody RecurringPaymentsDTO recurringPaymentsDTO) {
        CustomUserDetail myUserDetails = (CustomUserDetail) authentication.getPrincipal();
        return userService.setupNewRecurringPayment(myUserDetails.getId(), recurringPaymentsDTO);

    }

    @GetMapping("/recurring_payment")
    public List<RecurringPaymentsQDO> showRecurringPayments(Authentication authentication){
        CustomUserDetail myUserDetails = (CustomUserDetail) authentication.getPrincipal();
        return userService.showRecurringPayments(myUserDetails.getId());
    }

    @GetMapping("/balance")
    public int getBalance(Authentication authentication){
        CustomUserDetail myUserDetails=(CustomUserDetail) authentication.getPrincipal();
        return userService.getBalance(myUserDetails.getUsername());
    }

    @PostMapping("/statement")
    public List<Transactions> showStatement(Authentication authentication, @RequestBody StatementDTO statementDTO){
        CustomUserDetail myUserDetail=(CustomUserDetail) authentication.getPrincipal();
        return userService.getStatement(myUserDetail.getId(),statementDTO.getFrom(),statementDTO.getTo());
    }

    @DeleteMapping("/delete_recurring/{recc_id}")
    public String deleteRecurringPayment(Authentication authentication,@PathVariable int recc_id){
        CustomUserDetail myUserDetail=(CustomUserDetail) authentication.getPrincipal();
        return userService.removeRecurringPayment(myUserDetail.getId(), recc_id);
    }

    
    @PutMapping("/payment-monthly")
    public String payMonthlyPayments(Authentication authentication){
        CustomUserDetail myUserDetails = (CustomUserDetail) authentication.getPrincipal();
        return userService.transferMonthlyPayments(myUserDetails.getId());
    }

}
