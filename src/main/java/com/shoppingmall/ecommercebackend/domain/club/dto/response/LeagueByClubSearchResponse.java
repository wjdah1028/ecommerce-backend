package com.shoppingmall.ecommercebackend.domain.club.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "리그별 구단 조회 응답 dto", description = "사용자가 리그별 구단 목록을 조회할때 서버가 반환하는 데이터")
public class LeagueByClubSearchResponse {

    @Schema(description = "리그 고유번호", example = "1")
    private Long leagueId;

    @Schema(description = "리그 이름", example = "리그 이름")
    private String leagueName;

    @Schema(description = "구단 고유번호", example = "1")
    private Long clubId;

    @Schema(description = "구단 이름", example = "맨체스터 유나이티드")
    private String clubName;
}
