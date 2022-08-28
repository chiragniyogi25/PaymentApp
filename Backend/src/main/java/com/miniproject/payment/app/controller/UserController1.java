//package com.miniproject.payment.app.controller;
//
//import com.miniproject.payment.app.dto.AddMoneyDTO;
//import com.miniproject.payment.app.dto.RecurringPaymentsDTO;
//import com.miniproject.payment.app.dto.RecurringPaymentsQDO;
//import com.miniproject.payment.app.entity.AuthenticationRequest;
//import com.miniproject.payment.app.dto.UserDTO;
//import com.miniproject.payment.app.entity.AuthenticationResponse;
//import com.miniproject.payment.app.entity.User;
//import com.miniproject.payment.app.jpaAuth.CustomUserDetail;
//import com.miniproject.payment.app.jpaAuth.MyUserDetailsService;
//import com.miniproject.payment.app.service.UserService;
//import com.miniproject.payment.app.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class UserController1 {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtUtil jwtTokenUtil;
//
//    @PostMapping("/signup")
//    public String addUser(@RequestBody UserDTO userDto){
//        UserDTO user=userDto;
//        System.out.println(user.getEmail());
//        System.out.println(user.getPassword());
//        return userService.saveUser(userDto);
//    }
//    @PostMapping("/signups")
//    public String addUsers(@RequestBody List<UserDTO> userDTOs){
//        return userService.saveUsers(userDTOs);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
//        try{
//            new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getEmail(),authenticationRequest.getPassword()
//            );
//        }catch (BadCredentialsException e){
//            throw new UsernameNotFoundException("Incorrect Email and Password");
//        }
//        final UserDetails userDetails=userDetailsService
//                .loadUserByUsername(authenticationRequest.getEmail());
//        final String jwt = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
//    }
//
//    @GetMapping("/users")
//    public List<UserDTO> getAllUsers(){
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/home")
//    public String homePage(@RequestHeader(name="Authorization") String token){
//        return "Hi! "+jwtTokenUtil.extractUsername(token.substring(7));
//    }
//
//    @PutMapping("/addMoney")
//    public String addMoney(Authentication authentication, @RequestBody AddMoneyDTO addMoneyDTO){
//        int amt=addMoneyDTO.getAmount();
//        CustomUserDetail customUserDetail=(CustomUserDetail) authentication.getPrincipal();
//        return userService.addMoney(customUserDetail.getId(),amt);
//    }
//
//    @PostMapping("/recurring_payment")
//    public String setupNewRecurringPayment(Authentication authentication, @RequestBody RecurringPaymentsDTO recurringPaymentsDTO) {
//        CustomUserDetail myUserDetails = (CustomUserDetail) authentication.getPrincipal();
//        return userService.setupNewRecurringPayment(myUserDetails.getId(), recurringPaymentsDTO);
//
//    }
//
//    @GetMapping("/recurring_payment")
//    public List<RecurringPaymentsQDO> showRecurringPayments(Authentication authentication){
//        CustomUserDetail myUserDetails = (CustomUserDetail) authentication.getPrincipal();
//        return userService.showRecurringPayments(myUserDetails.getId());
//    }
//
//}
