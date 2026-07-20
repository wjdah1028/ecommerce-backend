package com.shoppingmall.ecommercebackend.domain.league.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "리그 수정 응답 dto", description = "관리자가 리그를 수정할때 서버가 반환하는 데이터")
public class LeagueUpdateResponse {

    @Schema(description = "리그 고유번호", example = "1")
    private Long leagueId;

    @Schema(description = "리그 이름", example = "프리미어리그")
    private String leagueName;

    @Schema(description = "수정 시간", example = "2026-07-017T04:00:00")
    private LocalDateTime modifiedAt;
}
