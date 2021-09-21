package com.demo.hospital.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    Logger logger= LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("Responding with unauthorized error. Message - {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Sorry, You're not authorized to access this resource.");
    }

}
