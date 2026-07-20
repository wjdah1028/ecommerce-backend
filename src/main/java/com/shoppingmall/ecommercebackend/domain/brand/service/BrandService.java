package com.shoppingmall.ecommercebackend.domain.brand.service;

import com.shoppingmall.ecommercebackend.domain.brand.dto.request.BrandRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.brand.entity.BrandEntity;
import com.shoppingmall.ecommercebackend.domain.brand.exception.BrandErrorCode;
import com.shoppingmall.ecommercebackend.domain.brand.repository.BrandRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;

    // 브랜드 등록
    @Transactional
    public BrandRegisterResponse registerBrand(BrandRegisterRequest request) {

        // 브랜드가 중복되는지 조회
        if (brandRepository.existsByBrandName(request.getBrandName())) {
            log.warn("[BrandService] 등록된 브랜드입니다");
            throw new CustomException(BrandErrorCode.BRAND_DUPLICATE);
        }

        // 브랜드 객체 생성
        BrandEntity brand = BrandEntity.builder()
                .brandName(request.getBrandName())
                .build();

        // DB 저장
        BrandEntity savedBrand = brandRepository.save(brand);

        // 로그 출력
        log.info("[BrandService] 브랜드 등록 성공: brandId={}", savedBrand.getBrandId());

        // 응답 세팅
        return BrandRegisterResponse.builder()
                .brandId(savedBrand.getBrandId())
                .brandName(savedBrand.getBrandName())
                .createdAt(savedBrand.getCreatedAt())
                .build();
    }
}
