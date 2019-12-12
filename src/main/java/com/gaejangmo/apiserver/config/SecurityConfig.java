package com.gaejangmo.apiserver.config;

import com.gaejangmo.apiserver.config.oauth.service.CustomOAuth2UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(final CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/api/login/state", "/h2-console/**", "/oauth2/redirect").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**/users/products/**").permitAll()
                .antMatchers("/api/**/users/products/**").hasRole("USER")
                .anyRequest().authenticated();

        http.httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.oauth2Login()
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
