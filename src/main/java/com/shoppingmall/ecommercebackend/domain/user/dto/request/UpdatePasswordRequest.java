package com.shoppingmall.ecommercebackend.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "비밀번호 수정 요청 dto", description = "사용자가 비밀번호를 수정할때 서버에 요청 보내는 데이터")
public class UpdatePasswordRequest {

    @Schema(description = "현재 비밀번호", example = "c*123456789")
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String password;

    @Schema(description = "새로운 비밀번호", example = "c*987654321")
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 영문 대,소문자, 숫자, 특수기호를 포함한 8~15자리여야 합니다.")
    private String newPassword;
}
