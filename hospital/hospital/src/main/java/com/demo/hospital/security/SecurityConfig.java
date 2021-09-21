package com.demo.hospital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider customAuthProvider;

    final
    JwtAuthenticationEntryPoint unauthorizedHandler;

    final
    UserServiceImpl userServiceImpl;

    final
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, UserServiceImpl userServiceImpl, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userServiceImpl = userServiceImpl;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
//                .antMatchers(
//                       HttpMethod.POST,Urls.VERSION_V10+Urls.USER
//                )
//                .permitAll()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/demo/v1/user/sign-up",
                        "/demo/v1/user/otp-verification",
                        "/demo/v1/user/resend-otp-verification",
                        "/demo/v1/user/login"
                        )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(customAuthProvider);
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder().encode("pass1"))
                .roles("USER")
                .and()
                .withUser("user2")
                .password(passwordEncoder().encode("pass2"))
                .roles("USER");
    }

    @Override
    public void configure(WebSecurity web) {

        web.ignoring().antMatchers(
                "/demo",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

}