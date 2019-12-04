package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserProductService {
    private final UserProductRepository userProductRepository;

    public UserProductService(final UserProductRepository userProductRepository) {
        this.userProductRepository = userProductRepository;
    }

    public UserProductResponseDto save(final UserProductCreateDto userProductCreateDto) {
        // save image

        // toEntity

        // repository save

        // toResponseDto

        return null;
    }
}
