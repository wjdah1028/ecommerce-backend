package com.shoppingmall.ecommercebackend.domain.league.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "리그 조회 응답 dto", description = "사용자가 리그 목록을 조회할때 서버가 반환하는 데이터")
public class LeagueSearchResponse {

    @Schema(description = "리그 고유번호", example = "1")
    private Long leagueId;

    @Schema(description = "리그 이름", example = "프리미어리그")
    private String leagueName;
}
