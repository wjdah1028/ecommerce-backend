package com.shoppingmall.ecommercebackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "사용자 정보 수정 dto", description = "사용자가 비밀번호를 제외한 사용자 정보를 수정할때 서버에 요청 보내는 데이터")
public class UpdateUserRequest {

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Schema(description = "닉네임", example = "홍홍홍")
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
}
