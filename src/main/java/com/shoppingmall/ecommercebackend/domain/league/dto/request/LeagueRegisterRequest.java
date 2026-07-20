package com.shoppingmall.ecommercebackend.domain.league.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Schema(title = "리그 등록 요청 dto", description = "관리자가 리그를 등록할 때 서버에 요청 보내는 데이터")
public class LeagueRegisterRequest {

    @Schema(description = "리그 이름", example = "프리미어리그")
    @NotBlank(message = "리그 이름은 필수 입력값입니다.")
    private String leagueName;
}
