package com.shoppingmall.ecommercebackend.domain.stock.dto.response;

import com.shoppingmall.ecommercebackend.domain.stock.entity.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "특정 유니폼 재고 전체 조회 응답 dto", description = "판매자가 특정 유니폼 재고를 조회할때 서버가 반환하는 데이터")
public class StockAllCountResponse {

    @Schema(description = "유니폼 고유번호", example = "1")
    private Long uniformId;

    @Schema(description = "유니폼 이름", example = "맨체스터 유나이티드 26/27 어센틱 홈 저지")
    private String uniformName;

    @Schema(description = "사이즈", example = "M")
    private Size size;

    @Schema(description = "수량", example = "10")
    private Integer stockQuantity;
}
