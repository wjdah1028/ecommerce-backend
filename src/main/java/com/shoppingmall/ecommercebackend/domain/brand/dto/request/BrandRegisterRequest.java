package com.shoppingmall.ecommercebackend.domain.brand.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "브랜드 등록 요청 dto", description = "관리자가 브랜드를 등록할 때 서버에 요청 보내는 데이터")
public class BrandRegisterRequest {

    @Schema(description = "브랜드 이름", example = "나이키")
    @NotBlank(message = "브랜드 이름은 필수 입력값입니다.")
    private String brandName;
}
