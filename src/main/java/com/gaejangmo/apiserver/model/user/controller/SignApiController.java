package com.gaejangmo.apiserver.model.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class SignApiController {

    @GetMapping("/login/state")
    public ResponseEntity<Boolean> checkLogin(final OAuth2AuthenticationToken authentication) {
        if (Objects.isNull(authentication)) {
            return ResponseEntity.ok().body(Boolean.FALSE);
        }

        return ResponseEntity.ok().body(authentication.isAuthenticated());
    }
}
