package com.gaejangmo.apiserver.model.notice.controller;

import com.gaejangmo.apiserver.model.notice.dto.NoticeRequestDto;
import com.gaejangmo.apiserver.model.notice.dto.NoticeResponseDto;
import com.gaejangmo.apiserver.model.notice.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/notice")
public class NoticeApiController {
    private final NoticeService noticeService;

    public NoticeApiController(final NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> findNoticeResponse(@PathVariable long id) {
        NoticeResponseDto responseDto = noticeService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<NoticeResponseDto> save(@RequestBody @Valid NoticeRequestDto noticeRequestDto) {
        NoticeResponseDto notice = noticeService.save(noticeRequestDto);
        return ResponseEntity.created(linkTo(NoticeApiController.class)
                .slash(notice.getId()).toUri())
                .body(notice);
    }
}
