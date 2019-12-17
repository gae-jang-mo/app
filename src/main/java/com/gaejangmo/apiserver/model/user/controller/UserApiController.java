package com.gaejangmo.apiserver.model.user.controller;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.image.dto.FileResponseDto;
import com.gaejangmo.apiserver.model.user.dto.UserResponseDto;
import com.gaejangmo.apiserver.model.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {
    private final UserService userService;

    public UserApiController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logined")
    public ResponseEntity<UserResponseDto> find(@LoginUser SecurityUser user) {
        UserResponseDto response = userService.findUserResponseDtoByOauthId(user.getOauthId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/image")
    public ResponseEntity<FileResponseDto> updateUserImage(@RequestParam("file") final MultipartFile multipartFile,
                                                           @LoginUser final SecurityUser securityUser) {

        FileResponseDto fileResponseDto = userService.updateUserImage(multipartFile, securityUser.getId());
        return ResponseEntity.ok(fileResponseDto);
    }
}
