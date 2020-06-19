package com.cx.prod.list.config.security;

import com.cx.prod.list.filter.JwtFilter;
import com.cx.prod.list.repos.security.AuthorizationRequestRepositoryRedirectDecorator;
import com.cx.prod.list.security.handlers.Oauth2FailHandler;
import com.cx.prod.list.security.handlers.Oauth2SuccessHandler;
import com.cx.prod.list.services.oauth.Oauth2UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
/*@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)*/
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Oauth2SuccessHandler oauth2SuccessHandler;
    @Autowired
    private Oauth2FailHandler oauth2FailHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/",
                "/error",
                "/js/**/*.js",
                "/css/**/*.css",
                "/image/**/*.png",
                "/image/**/*.gif",
                "/image/**/*.jpg",
                "/auth/**",
                "/oauth2/**",
                "/products/list")
                .permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oauth2UsrService())
                .and()
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .disable()
                .formLogin()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new ApiAuthenticationEntryPoint())
                .and()
                .csrf()
                .disable()
                .cors();

        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public AuthorizationRequestRepositoryRedirectDecorator authorizationRequestRepository() {
        return new AuthorizationRequestRepositoryRedirectDecorator(new HttpSessionOAuth2AuthorizationRequestRepository());
    }

    @Bean
    public Oauth2UsrService oauth2UsrService() {
        return new Oauth2UsrService();
    }

}
