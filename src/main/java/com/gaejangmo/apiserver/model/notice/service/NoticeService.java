package com.gaejangmo.apiserver.model.notice.service;

import com.gaejangmo.apiserver.model.notice.domain.Notice;
import com.gaejangmo.apiserver.model.notice.domain.NoticeRepository;
import com.gaejangmo.apiserver.model.notice.domain.NoticeType;
import com.gaejangmo.apiserver.model.notice.dto.NoticeRequestDto;
import com.gaejangmo.apiserver.model.notice.dto.NoticeResponseDto;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(final NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeResponseDto findByCreatedDate(final String createdDate) {
        Notice notice = noticeRepository.findByCreatedDate(createdDate).orElseThrow(RuntimeException::new);
        return toDto(notice);
    }

    public NoticeResponseDto save(final NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRepository.save(toEntity(noticeRequestDto));
        return toDto(notice);
    }

    private NoticeResponseDto toDto(final Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .noticeType(notice.getNoticeType())
                .header(notice.getHeader())
                .contents(notice.getContents())
                .noticeCreatedDate(notice.getCreatedDate())
                .build();
    }

    private Notice toEntity(final NoticeRequestDto noticeRequestDto) {
        return Notice.builder()
                .noticeType(NoticeType.valueOf(noticeRequestDto.getNoticeType()))
                .header(noticeRequestDto.getHeader())
                .contents(noticeRequestDto.getContents())
                .build();
    }
}
