package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {
    private final UserService userService;

    public UserApiController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logined")
    public ResponseEntity<UserResponseDto> find(HttpSession session) {
        User user = (User) session.getAttribute("user");
        UserResponseDto response = userService.findUserResponseDtoByOauthId(user.getOauthId());
        return ResponseEntity.ok().body(response);
    }
}
