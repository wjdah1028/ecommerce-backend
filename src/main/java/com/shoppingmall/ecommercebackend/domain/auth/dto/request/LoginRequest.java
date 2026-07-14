package com.shoppingmall.ecommercebackend.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "로그인 요청 DTO", description = "이메일과 비밀번호로 로그인할때 서버에 요청 보내는 데이터")
public class LoginRequest {

    @NotBlank(message = "사용자 이메일 항목은 필수입니다.")
    @Schema(description = "이메일", example = "test@naver.com")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "사용자 비밀번호 항목은 필수입니다.")
    @Schema(description = "비밀번호", example = "c*123456789")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 영문 대,소문자, 숫자, 특수기호를 포함한 8~15자리여야 합니다.")
    private String password;
}
