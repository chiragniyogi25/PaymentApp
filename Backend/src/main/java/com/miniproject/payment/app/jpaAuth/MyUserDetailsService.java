package com.miniproject.payment.app.jpaAuth;

import com.miniproject.payment.app.entity.User;
import com.miniproject.payment.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=this.userRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("NO USER");
        }
        return new CustomUserDetail(user);
    }
}
