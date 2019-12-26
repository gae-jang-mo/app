package com.gaejangmo.apiserver.model.like.controller;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.common.exception.ApiErrorResponse;
import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.like.service.LikeService;
import com.gaejangmo.apiserver.model.user.dto.UserSearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{targetId}")
    public ResponseEntity delete(@PathVariable Long targetId, @LoginUser SecurityUser securityUser) {
        likeService.deleteBySourceAndTarget(securityUser.getId(), targetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<UserSearchDto>> ranking(@PageableDefault final Pageable pageable) {
        List<UserSearchDto> ranking = likeService.findRanking(pageable);
        return ResponseEntity.ok(ranking);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleException(final Exception exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }
}
