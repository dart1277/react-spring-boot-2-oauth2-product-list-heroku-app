package com.cx.prod.list.security.services;


import com.cx.prod.list.model.app.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder pswEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.equals("cx")) {
            return new AppUser("cx", pswEncoder.encode("ok"), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }
}
