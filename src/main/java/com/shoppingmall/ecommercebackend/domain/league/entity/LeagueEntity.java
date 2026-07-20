package com.shoppingmall.ecommercebackend.domain.league.entity;

import com.shoppingmall.ecommercebackend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "leagues")
public class LeagueEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leagueId;

    @Column(nullable = false, length = 100)
    private String leagueName;

    // 리그 수정 메서드
    public void updateLeague(String leagueName) {
        this.leagueName = leagueName;
    }
}
