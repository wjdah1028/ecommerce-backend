package com.shoppingmall.ecommercebackend.domain.uniform.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "유니폼 등록 요청 dto", description = "판매자가 유니폼을 등록할 때 서버에 요청 보내는 데이터")
public class UniformRegisterRequest {

    @Schema(description = "유니폼 이름", example = "맨체스터 유나이티드 26/27 어센틱 홈 저지")
    @NotBlank(message = "유니폼 이름은 필수 입력값입니다.")
    private String uniformName;

    @Schema(description = "브랜드 고유번호", example = "1")
    @NotNull(message = "브랜드 번호는 필수 입력값입니다.")
    private Long brandId;

    @Schema(description = "구단 고유번호", example = "1")
    @NotNull(message = "구단 번호는 필수 입력값입니다.")
    private Long clubId;

    @Schema(description = "유니폼 이미지", example = "https://..")
    private String uniformImage;

    @Schema(description = "유니폼 가격", example = "199000")
    @NotNull(message = "유니폼 가격은 필수 입력값입니다.")
    private Integer price;
}
