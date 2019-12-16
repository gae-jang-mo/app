package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.commons.logging.EnableLog;
import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.common.resolver.SessionUser;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.domain.vo.Motto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @EnableLog
    @PutMapping("/")
    public ResponseEntity<Motto> updateMotto(@RequestBody final Motto motto,
                                             @LoginUser SessionUser sessionUser) {
        userService.updateMotto(motto, sessionUser.getId());
        return ResponseEntity.ok().body(motto);
    }
}
