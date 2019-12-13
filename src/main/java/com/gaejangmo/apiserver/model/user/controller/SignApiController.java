package com.gaejangmo.apiserver.model.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class SignApiController {

    @GetMapping("/login/state")
    public ResponseEntity<Boolean> checkLogin(final Principal principal) {
        if (Objects.isNull(principal)) {
            return ResponseEntity.ok().body(Boolean.FALSE);
        }

        return ResponseEntity.ok().body(Boolean.TRUE);
    }
}
