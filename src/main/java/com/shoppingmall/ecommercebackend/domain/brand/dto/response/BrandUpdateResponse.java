package com.shoppingmall.ecommercebackend.domain.brand.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "브랜드 수정 응답 dto", description = "관리자가 브랜드를 수정할때 서버가 반환하는 데이터")
public class BrandUpdateResponse {

    @Schema(description = "브랜드 고유번호", example = "1")
    private Long brandId;

    @Schema(description = "브랜드 이름", example = "나이키")
    private String brandName;

    @Schema(description = "수정 시간", example = "2026-07-017T04:00:00")
    private LocalDateTime modifiedAt;
}
