package com.demo.hospital.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${app.jwtHeaderString}")
    private String accessToken;

    @Value("${app.jwtSecret}")
    private String jwtSecrete;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    String generateToken(Authentication authentication ){

        //rutul
//        UserPrinciple userPrincipal=UserPrinciple.create1(user);

        UserPrinciple userPrincipal= (UserPrinciple) authentication.getPrincipal();
        Date now=new Date();

//        Date currentTime = new Date(System.currentTimeMillis());
//        try {
//            long expiryDateMili=System.currentTimeMillis()+jwtExpirationInMs;
//            expiryDate=DateFormat.getInstance().parse(now.getTime()+jwtExpirationInMs);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Date expiryDate=new Date(now.getTime()+jwtExpirationInMs);


        return Jwts.builder().setId(String.valueOf(userPrincipal.getId()))
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(jwtSecrete.getBytes()))
                .compact();
    }

    public Integer getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(jwtSecrete.getBytes()))
                .parseClaimsJws(token)
                .getBody();
        return Integer.valueOf(claims.getId());
    }

    boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(jwtSecrete.getBytes()))
                    .parseClaimsJws(jwt);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        } catch (Exception ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
