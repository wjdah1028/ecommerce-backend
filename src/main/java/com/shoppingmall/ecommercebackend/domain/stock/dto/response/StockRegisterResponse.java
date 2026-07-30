package com.shoppingmall.ecommercebackend.domain.stock.dto.response;

import com.shoppingmall.ecommercebackend.domain.stock.entity.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "재고 등록 응답 dto", description = "판매자가 재고를 등록할때 서버가 반환하는 데이터")
public class StockRegisterResponse {

    @Schema(description = "유니폼 고유번호", example = "1")
    private Long uniformId;

    @Schema(description = "유니폼 이름", example = "맨체스터 유나이티드 26/27 어센틱 홈 저지")
    private String uniformName;

    @Schema(description = "사이즈", example = "M")
    private Size size;

    @Schema(description = "수량", example = "10")
    private Integer stockQuantity;

    @Schema(description = "재고 등록 시간", example = "2026-07-017T04:00:00")
    private LocalDateTime createdAt;
}
