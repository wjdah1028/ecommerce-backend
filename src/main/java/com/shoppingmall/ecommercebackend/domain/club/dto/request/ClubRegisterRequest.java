package com.shoppingmall.ecommercebackend.domain.club.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "구단 등록 요청 dto", description = "관리자가 구단을 등록할 때 서버에 요청 보내는 데이터")
public class ClubRegisterRequest {

    @Schema(description = "구단 이름", example = "맨체스터 유나이티드")
    @NotBlank(message = "구단 이름은 필수 입력 값입니다.")
    private String clubName;
}
