package com.shoppingmall.ecommercebackend.domain.brand.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "브랜드 조회 응답 dto", description = "사용자가 브랜드 목록을 조회할때 서버가 반환하는 데이터")
public class BrandSearchResponse {

    @Schema(description = "브랜드 고유번호", example = "1")
    private Long brandId;

    @Schema(description = "브랜드 이름", example = "나이키")
    private String brandName;
}
