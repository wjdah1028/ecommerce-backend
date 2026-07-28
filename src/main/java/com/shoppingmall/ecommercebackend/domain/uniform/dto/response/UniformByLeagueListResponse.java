package com.shoppingmall.ecommercebackend.domain.uniform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "리그별 유니폼 전체 조회 응답 dto", description = "사용자가 리그별 유니폼 전체 조회할때 서버가 반환하는 데이터")
public class UniformByLeagueListResponse {

    @Schema(description = "리그 고유번호", example = "1")
    private Long leagueId;

    @Schema(description = "리그 이름", example = "프리미어리그")
    private String leagueName;

    @Schema(description = "유니폼 고유번호", example = "1")
    private Long uniformId;

    @Schema(description = "유니폼 이름", example = "맨체스터 유나이티드 26/27 어센틱 홈 저지")
    private String uniformName;

    @Schema(description = "유니폼 사진", example = "https://..")
    private String uniformImage;

    @Schema(description = "유니폼 가격", example = "199000")
    private Integer price;
}
