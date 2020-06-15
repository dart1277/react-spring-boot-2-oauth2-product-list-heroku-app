package com.cx.prod.list.repos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorizationRequestRepositoryRedirectDecorator implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestRepository;
    @Value("${app.redirectUriParamName}")
    private String REDIRECT_URI_PARAM_NAME;

    public AuthorizationRequestRepositoryRedirectDecorator(AuthorizationRequestRepository<OAuth2AuthorizationRequest> requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return requestRepository.removeAuthorizationRequest(request, response);
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return requestRepository.loadAuthorizationRequest(request);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
        }
        session.removeAttribute(REDIRECT_URI_PARAM_NAME);
        session.setAttribute(REDIRECT_URI_PARAM_NAME, request.getParameter(REDIRECT_URI_PARAM_NAME));
        requestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return requestRepository.removeAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestRedirectUri(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(REDIRECT_URI_PARAM_NAME);
        }
    }

    public String getAuthorizationRequestRedirectUri(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String redirectUri = (String) session.getAttribute(REDIRECT_URI_PARAM_NAME);
            return redirectUri != null ? redirectUri : "";
        }
        return "";
    }
}
