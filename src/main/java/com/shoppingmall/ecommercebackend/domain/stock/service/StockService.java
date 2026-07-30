package com.shoppingmall.ecommercebackend.domain.stock.service;

import com.shoppingmall.ecommercebackend.domain.stock.dto.request.StockRegisterRequest;
import com.shoppingmall.ecommercebackend.domain.stock.dto.response.StockAllCountResponse;
import com.shoppingmall.ecommercebackend.domain.stock.dto.response.StockRegisterResponse;
import com.shoppingmall.ecommercebackend.domain.stock.entity.StockEntity;
import com.shoppingmall.ecommercebackend.domain.stock.repository.StockRepository;
import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
import com.shoppingmall.ecommercebackend.domain.uniform.exception.UniformErrorCode;
import com.shoppingmall.ecommercebackend.domain.uniform.repository.UniformRepository;
import com.shoppingmall.ecommercebackend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StockService {

    private final StockRepository stockRepository;
    private final UniformRepository uniformRepository;

    // 재고 등록
    @Transactional
    public StockRegisterResponse stockRegister(StockRegisterRequest request, Long uniformId, Long userId) {

        // 유니폼이 존재하는지 조회
        UniformEntity uniform = uniformRepository.findById(uniformId)
                .orElseThrow(() -> new CustomException(UniformErrorCode.UNIFORM_NOT_FOUND));

        // 판매자 본인이 올린 유니폼인지 조회
        if (!uniform.getUser().getUserId().equals(userId)) {
            log.warn("[StockService] 유니폼 재고를 등록할 권한이 없습니다.");
            throw new CustomException(UniformErrorCode.UNIFORM_NOT_AUTHORITY);
        }

        // 재고 객체 변수 생성
        StockEntity stock;

        // 같은 유니폼 + 같은 사이즈일때 재고가 있으면 수량 추가 || 재고가 없으면 새로운 재고 객체 생성
        Optional<StockEntity> exist = stockRepository.findByUniformAndSize(uniform, request.getSize());
        if (exist.isPresent()) {
            stock = exist.get();
            stock.StockPlus(request.getStockQuantity());
        }
        else {
            stock = StockEntity.builder()
                    .uniform(uniform)
                    .size(request.getSize())
                    .stockQuantity(request.getStockQuantity())
                    .build();

            // DB 저장
            stockRepository.save(stock);
        }

        // 로그 출력
        log.info("[StockService] 재고 등록 성공: stockId= {}", stock.getStockId());

        // 응답 세팅
        return StockRegisterResponse.builder()
                .uniformId(stock.getUniform().getUniformId())
                .uniformName(stock.getUniform().getUniformName())
                .size(stock.getSize())
                .stockQuantity(stock.getStockQuantity())
                .createdAt(stock.getCreatedAt())
                .build();
    }

    // 특정 유니폼 재고 목록 전체 조회
    public List<StockAllCountResponse> stockAllCount(Long uniformId, Long userId) {

        // 유니폼이 존재하는지 조회
        UniformEntity uniform = uniformRepository.findById(uniformId)
                .orElseThrow(() -> new CustomException(UniformErrorCode.UNIFORM_NOT_FOUND));

        // 판매자가 올린 유니폼인지 조회
        if (!uniform.getUser().getUserId().equals(userId)) {
            log.warn("[StockService] 해당 재고를 조회할 권한이 없습니다.");
            throw new CustomException(UniformErrorCode.UNIFORM_NOT_AUTHORITY);
        }

        // 응답 세팅
        List<StockAllCountResponse> list = new ArrayList<>();
        for (StockEntity stock : stockRepository.findAllByUniform(uniform)) {
            list.add(StockAllCountResponse.builder()
                    .uniformId(stock.getUniform().getUniformId())
                    .uniformName(stock.getUniform().getUniformName())
                    .size(stock.getSize())
                    .stockQuantity(stock.getStockQuantity())
                    .build());
        }

        // 로그 출력
        log.info("[StockService] 특정 유니폼 재고 전체 목록 조회 성공");

        return list;
    }
}
