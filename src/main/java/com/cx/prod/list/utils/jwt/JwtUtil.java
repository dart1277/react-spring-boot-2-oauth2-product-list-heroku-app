package com.cx.prod.list.utils.jwt;

import com.cx.prod.list.model.app.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${app.jwt.expireTime}")
    private Long expireTime;
    @Value("${app.jwt.secret}")
    private String secret;


    public String generate(Authentication authentication, HttpServletRequest request) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        long currentTimeMillis = System.currentTimeMillis();
        Date expiresAt = new Date(currentTimeMillis + expireTime);

        return Jwts.builder()
                .setSubject(appUser.getAccountName())
                .setIssuer(request.getServletPath())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, generateKey(secret))
                .compact();
    }

    public String getAccountName(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(generateKey(secret))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String bearer = "Bearer ";
        if (header != null && header.startsWith(bearer)) {
            return header.substring(bearer.length());
        } else {
            return null;
        }
    }

    public boolean isTokenValid(String authToken) {
        boolean valid = false;
        try {
            Jwts.parser().setSigningKey(generateKey(secret)).parseClaimsJws(authToken);
            valid = true;
        } catch (Exception ex) {
            logger.error("JWT parsing failed", ex);
        }
        return valid;
    }

    public SecretKey generateKey(String string) {
        return new SecretKeySpec(string.getBytes(), 0, string.getBytes().length, "DES");
    }
}
