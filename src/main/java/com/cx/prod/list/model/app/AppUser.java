package com.cx.prod.list.model.app;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class AppUser implements UserDetails, OAuth2User {
    private static final String[] attrNames = new String[]{"sub", "id"};
    private String userName;
    private String accountName;
    private String email;
    private String passwordHash;
    private Map<String, Object> attributes;
    private List<GrantedAuthority> authorities;

    public static AppUser getInstance(OAuth2User oAuth2User) {
        return new AppUser(oAuth2User.getName(), getAcctNameAttribute(oAuth2User),
                oAuth2User.getAttribute("email"), oAuth2User.getAttributes());
    }

    public AppUser(String userName, String accountName, String email, Map<String, Object> attributes) {
        this(userName, accountName, email, attributes, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public AppUser(String userName, String accountName, String email, Map<String, Object> attributes, List<GrantedAuthority> authorities) {
        this.userName = userName;
        this.accountName = accountName;
        this.email = email;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    public AppUser(String userName, String passwordHash, List<GrantedAuthority> authorities){
       this.userName = userName;
       this.passwordHash = passwordHash;
       this.authorities = authorities;
    }

    @Override
    public String getName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountName() {
        return accountName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static String getAcctNameAttribute(OAuth2User oa2User) {
        Optional<Object> acctNameOpt = Arrays.stream(attrNames).map(oa2User::getAttribute).filter(Objects::nonNull).findFirst();
        if (acctNameOpt.isPresent()) {
            Object acctName = acctNameOpt.get();
            if (acctName instanceof String) {
                return (String) acctName;
            } else {
                return acctNameOpt.toString();
            }
        }
        return "";
    }
}
