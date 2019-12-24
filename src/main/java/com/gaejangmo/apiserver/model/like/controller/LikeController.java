package com.gaejangmo.apiserver.model.like.controller;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(final LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{targetId}")
    public ResponseEntity save(@PathVariable Long targetId, @LoginUser SecurityUser securityUser) {
        likeService.save(securityUser.getId(), targetId);
        return ResponseEntity.ok().build();
    }
}
