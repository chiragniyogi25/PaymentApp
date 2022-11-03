package com.miniproject.payment.app.filters;

import com.miniproject.payment.app.jpaAuth.MyUserDetailsService;
import com.miniproject.payment.app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//class used for intercepting the request calls to check for valid token
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;


    //similar to doFilter in JSP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader=request.getHeader("Authorization");
        //Bearer accesstoken

        String email_id=null;
        String jwt=null;

        if(authorizationHeader!=null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")){
            jwt=authorizationHeader.substring(7);//getting token
            email_id=jwtTokenUtil.extractUsername(jwt);//getting email id
        }

        //if certain username,password token has not been stored in SecurityContextHolder
        if(email_id!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(email_id);
            if(jwtTokenUtil.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                System.out.println("Not validated");
            }
        }
        filterChain.doFilter(request,response);
    }
}
