package com.shoppingmall.ecommercebackend.domain.club.entity;

import com.shoppingmall.ecommercebackend.domain.league.entity.LeagueEntity;
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
@Table(name = "clubs")
public class ClubEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    @Column(nullable = false, length = 200)
    private String clubName;
}
