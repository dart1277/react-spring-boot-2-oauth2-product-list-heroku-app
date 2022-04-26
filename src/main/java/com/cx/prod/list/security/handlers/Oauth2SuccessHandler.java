package com.cx.prod.list.security.handlers;

import com.cx.prod.list.repos.security.AuthorizationRequestRepositoryRedirectDecorator;
import com.cx.prod.list.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AuthorizationRequestRepositoryRedirectDecorator authorizationRequestRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtUtil.generate(authentication, request);

/*        String targetUrl = UriComponentsBuilder.fromUriString(authorizationRequestRepository.getAuthorizationRequestRedirectUri(request))
                .queryParam("token", token)
                .build().toUriString();
        authorizationRequestRepository.removeAuthorizationRequestRedirectUri(request);
        request.getSession().invalidate();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);*/

        UriComponentsBuilder targetUrl = UriComponentsBuilder.fromUriString("/login/auth");
        response.setHeader("Set-Cookie", "token=" +token+ "; HttpOnly=true; Secure=true; Path=/; SameSite=strict; Domain=localhost"); // SameSite=lax
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        authorizationRequestRepository.removeAuthorizationRequestRedirectUri(request);
        request.getSession().invalidate();
        response.setStatus(200);
    }

}
