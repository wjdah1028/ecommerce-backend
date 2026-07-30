package com.shoppingmall.ecommercebackend.domain.stock.dto.request;

import com.shoppingmall.ecommercebackend.domain.stock.entity.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "재고 등록 요청 dto", description = "판매자가 재고를 등록할 때 서버에 요청 보내는 데이터")
public class StockRegisterRequest {

    @Schema(description = "재고 사이즈", example = "M")
    @NotNull(message = "재고 사이즈는 필수 입력값입니다.")
    private Size size;

    @Schema(description = "등록할 수량", example = "10")
    @NotNull(message = "수량은 필수 입력값입니다.")
    private Integer stockQuantity;
}
