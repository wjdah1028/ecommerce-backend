package com.shoppingmall.ecommercebackend.domain.uniform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "유니폼 단건 조회 응답 dto", description = "사용자가 유니폼 단건을 조회할때 서버가 반환하는 데이터")
public class UniformSearchResponse {

    @Schema(description = "유니폼 고유번호", example = "1")
    private Long uniformId;

    @Schema(description = "유니폼 이름", example = "맨체스터 유나이티드 26/27 어센틱 홈 저지")
    private String uniformName;

    @Schema(description = "유니폼 사진", example = "https://..")
    private String uniformImage;

    @Schema(description = "유니폼 가격", example = "199000")
    private Integer price;

    @Schema(description = "브랜드 이름", example = "나이키")
    private String brandName;

    @Schema(description = "리그 이름", example = "프리미어리그")
    private String leagueName;

    @Schema(description = "구단 이름", example = "맨체스터 유나이티드 FC")
    private String clubName;
}
