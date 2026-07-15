package com.shoppingmall.ecommercebackend.domain.user.dto.response;

import com.shoppingmall.ecommercebackend.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "사용자 본인 정보 조회 response dto", description = "사용자 본인의 정보 조회할때 응답하는 데이터")
public class UserInfoResponse {

    @Schema(description = "사용자 고유번호", example = "1")
    private Long userId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @Schema(description = "사용자 이메일", example = "test@naver.com")
    private String email;

    @Schema(description = "사용자 전화번호", example = "010-1111-111")
    private String phone;

    @Schema(description = "사용자 닉네임", example = "홍홍홍")
    private String nickname;

    @Schema(description = "사용자 권한", example = "BUYER")
    private Role role;
}
