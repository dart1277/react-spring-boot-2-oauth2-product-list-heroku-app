package com.cx.prod.list.services.oauth;

import com.cx.prod.list.model.app.AppUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Oauth2UsrService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return AppUser.getInstance(oAuth2User);
        } catch (Exception ex) {
            throw new BadCredentialsException("Auth err", ex);
        }
    }

}
