package com.demo.hospital.security;

import com.demo.hospital.user.entity.User;
import com.demo.hospital.user.user_repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwtExpirationInMs}")
    long EXPIRATION_TIME;

    @Value("${app.jwtSecret}")
    String jwtSecrete;

    @Value("${app.jwtHeaderString}")
    String accessToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {

                Integer userId = jwtTokenProvider.getUserIdFromJwt(jwt);
                UserDetails userDetails = userService.loadUserByUserId(userId);
                request.setAttribute("id", userId);

                Optional<User> userModal = userRepository.findById(userId);


                if (userModal.isPresent()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        return request.getHeader(accessToken);
    }
}
