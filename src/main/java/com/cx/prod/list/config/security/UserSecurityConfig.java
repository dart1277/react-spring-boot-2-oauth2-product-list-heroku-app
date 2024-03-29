package com.cx.prod.list.config.security;

import com.cx.prod.list.filter.JwtFilter;
import com.cx.prod.list.repos.security.AuthorizationRequestRepositoryRedirectDecorator;
import com.cx.prod.list.security.handlers.Oauth2FailHandler;
import com.cx.prod.list.security.handlers.Oauth2SuccessHandler;
import com.cx.prod.list.security.web.SecureCookieCsrfTokenRepository;
import com.cx.prod.list.services.oauth.Oauth2UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

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

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/",
                "/error",
                "/*.txt",
                "/*.jpeg",
                "/*.jpg",
                "/*.png",
                "/*.gif",
                "/js/**/*.js",
                "/css/**/*.css",
                "/image/**/*.png",
                "/image/**/*.gif",
                "/image/**/*.jpg",
                "/static/js/**/*.js",
                "/static/css/**/*.css",
                "/static/media/**/*.png",
                "/static/media/**/*.gif",
                "/static/media/**/*.jpg",
                "/static/media/**/*.svg",
                "/oauth2/**",
                "/login/auth",
                "/products/list")
                .permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize") // "/oauth2/authorization"
                .authorizationRequestRepository(authorizationRequestRepository())
                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository, DEFAULT_AUTHORIZATION_REQUEST_BASE_URI))
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*") // "api/login2/*"
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
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable()
                .cors();
                //.configurationSource();

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
