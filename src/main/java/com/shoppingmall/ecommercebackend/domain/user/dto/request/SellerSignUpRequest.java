package com.shoppingmall.ecommercebackend.domain.user.dto.request;

import com.shoppingmall.ecommercebackend.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "판매자 회원가입 요청 dto", description = "판매자가 회원가입할때 서버에 요청 보내는 데이터")
public class SellerSignUpRequest {

    @Schema(description = "이름", example = "홍길순")
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Schema(description = "이메일", example = "test1@naver.com")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Schema(description = "비밀번호", example = "c*123456789")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", message = "비밀번호는 영문 대,소문자, 숫자, 특수기호를 포함한 8~15자리여야 합니다.")
    private String password;

    @Schema(description = "전화번호", example = "010-3333-3333")
    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    private String phone;

    @Schema(description = "닉네임", example = "판매자")
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;
}
