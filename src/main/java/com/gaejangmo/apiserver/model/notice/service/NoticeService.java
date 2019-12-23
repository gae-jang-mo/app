package com.gaejangmo.apiserver.model.notice.service;

import com.gaejangmo.apiserver.model.notice.domain.Notice;
import com.gaejangmo.apiserver.model.notice.domain.NoticeRepository;
import com.gaejangmo.apiserver.model.notice.domain.NoticeType;
import com.gaejangmo.apiserver.model.notice.dto.NoticeRequestDto;
import com.gaejangmo.apiserver.model.notice.dto.NoticeResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(final NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public NoticeResponseDto findById(final long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + "번 공지를 찾을 수 없습니다."));
        return toDto(notice);
    }

    public NoticeResponseDto save(final NoticeRequestDto noticeRequestDto) {
        Notice notice = noticeRepository.save(toEntity(noticeRequestDto));
        return toDto(notice);
    }

    public List<NoticeResponseDto> findAll(final Pageable pageable) {
        return noticeRepository.findAll(pageable)
                .map(this::toDto)
                .toList();
    }

    private NoticeResponseDto toDto(final Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .noticeType(notice.getNoticeType())
                .header(notice.getHeader())
                .contents(notice.getContents())
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
