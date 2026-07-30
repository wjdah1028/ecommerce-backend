package com.shoppingmall.ecommercebackend.domain.stock.dto.response;

import com.shoppingmall.ecommercebackend.domain.stock.entity.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "특정 유니폼 재고 품절 여부 응답 dto", description = "판매자가 특정 유니폼 재고 품절 여부를 서버가 반환하는 데이터")
public class StockSizeResponse {

    @Schema(description = "사이즈", example = "M")
    private Size size;

    @Schema(description = "품절 여부", example = "true")
    private boolean soldOut;
}
