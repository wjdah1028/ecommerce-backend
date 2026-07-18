package com.shoppingmall.ecommercebackend.domain.address.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "주소 수정 응답 dto", description = "사용자가 주소를 수정할 때 서버가 반환하는 데이터")
public class AddressUpdateResponse {

    @Schema(description = "주소 고유번호", example = "1")
    private Long addressId;

    @Schema(description = "사용자 고유번호", example = "1")
    private Long userId;

    @Schema(description = "주소(시)", example = "서울특별시")
    private String firstAddress;

    @Schema(description = "주소(구)", example = "성북구")
    private String secondAddress;

    @Schema(description = "주소(마지막)", example = "서경로 100")
    private String lastAddress;

    @Schema(description = "상세 주소", example = "501호")
    private String addressDetail;

    @Schema(description = "우편 번호", example = "111111")
    private String zipCode;

    @Schema(description = "기본 배송지 여부", example = "true")
    private boolean defaultAddress;

    @Schema(description = "주소 수정 시간", example = "2026-07-017T04:00:00")
    private LocalDateTime updatedAt;
}
