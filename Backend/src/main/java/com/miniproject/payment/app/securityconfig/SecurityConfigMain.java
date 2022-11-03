package com.miniproject.payment.app.securityconfig;

//import com.miniproject.payment.app.filters.JwtRequestFilter;
import com.miniproject.payment.app.filters.JwtRequestFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigMain extends WebSecurityConfigurerAdapter  {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(10);
    }
//    @Bean

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/actuator/*").permitAll()
                .antMatchers(HttpMethod.POST,"/signup","/login","/home").permitAll()
                .antMatchers(HttpMethod.PUT,"/addMoney").hasRole("USER")//ROLE_USER
                .antMatchers(HttpMethod.DELETE,"/delete_recurring/*").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/api/upload","/statement","/recurring_payment").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/recurring_payment").hasRole("USER")
                .anyRequest().authenticated()//login
                //since we have a filter telling spring security do create sessions
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//http stateless
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
