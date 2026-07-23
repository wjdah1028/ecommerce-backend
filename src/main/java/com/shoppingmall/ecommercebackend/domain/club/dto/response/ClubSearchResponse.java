package com.shoppingmall.ecommercebackend.domain.club.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "구단 조회 응답 dto", description = "사용자가 구단 목록을 조회할때 서버가 반환하는 데이터")
public class ClubSearchResponse {

    @Schema(description = "구단 고유번호", example = "1")
    private Long clubId;

    @Schema(description = "리그 고유번호", example = "1")
    private Long leagueId;

    @Schema(description = "리그 이름", example = "프리미어리그")
    private String leagueName;

    @Schema(description = "구단 이름", example = "맨체스터 유나이티드")
    private String clubName;
}
