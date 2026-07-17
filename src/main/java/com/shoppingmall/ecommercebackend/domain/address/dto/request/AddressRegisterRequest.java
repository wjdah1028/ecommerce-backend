package com.shoppingmall.ecommercebackend.domain.address.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "주소 등록 요청 dto", description = "사용자가 상품 배송 받을 주소를 등록할 때 서버에 요청 보내는 데이터")
public class AddressRegisterRequest {

    @Schema(description = "주소(시)", example = "서울특별시")
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String firstAddress;

    @Schema(description = "주소(구)", example = "성북구")
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String secondAddress;

    @Schema(description = "주소(마지막)", example = "서경로 100")
    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String lastAddress;

    @Schema(description = "상세 주소", example = "501호")
    @NotBlank(message = "상세 주소는 필수 입력 값입니다.")
    private String addressDetail;

    @Schema(description = "우편 번호", example = "11111")
    @NotBlank(message = "우편 번호는 필수 입력 값입니다.")
    private String zipCode;
}
