package com.shoppingmall.ecommercebackend.domain.brand.service;

import com.shoppingmall.ecommercebackend.domain.brand.dto.request.BrandRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.brand.dto.request.BrandUpdateRequest;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandSearchResponse;
import com.shoppingmall.ecommercebackend.domain.brand.dto.response.BrandUpdateResponse;
import com.shoppingmall.ecommercebackend.domain.brand.entity.BrandEntity;
import com.shoppingmall.ecommercebackend.domain.brand.exception.BrandErrorCode;
import com.shoppingmall.ecommercebackend.domain.brand.repository.BrandRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    // 브랜드 목록 조회
    public List<BrandSearchResponse> searchBrand() {

        // 응답 세팅
        List<BrandSearchResponse> list = new ArrayList<>();
        for (BrandEntity brand : brandRepository.findAll()) {
            list.add(BrandSearchResponse.builder()
                    .brandId(brand.getBrandId())
                    .brandName(brand.getBrandName())
                    .build());
        }

        // 로그 출력
        log.info("[BrandService] 브랜드 목록 조회 성공");

        return list;
    }

    // 브랜드 수정
    @Transactional
    public BrandUpdateResponse updateBrand(Long brandId, BrandUpdateRequest request) {

        // 브랜드가 존재하는지 조회
        BrandEntity brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new CustomException(BrandErrorCode.BRAND_NOT_FOUND));

        // 브랜드 수정
        brand.updateBrand(request.getBrandName());

        // 로그 출력
        log.info("[BrandService] 브랜드 수정 성공: brandId={}", brand.getBrandId());

        // 응답 세팅
        return BrandUpdateResponse.builder()
                .brandId(brand.getBrandId())
                .brandName(brand.getBrandName())
                .modifiedAt(brand.getModifiedAt())
                .build();
    }

    // 브랜드 삭제
    @Transactional
    public void deleteBrand(Long brandId) {

        // 브랜드가 존재하는지 조회
        BrandEntity brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new CustomException(BrandErrorCode.BRAND_NOT_FOUND));

        // 브랜드 삭제
        brandRepository.delete(brand);

        // 로그 출력
        log.info("[BrandService] 브랜드 삭제 성공: brandId={}", brand.getBrandId());
    }
}
