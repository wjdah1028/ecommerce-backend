package com.shoppingmall.ecommercebackend.domain.stock.entity;

import com.shoppingmall.ecommercebackend.domain.uniform.entity.UniformEntity;
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
@Table(name = "stocks")
public class StockEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uniform_id", nullable = false)
    private UniformEntity uniform;

    @Column(nullable = false)
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Size size;

    // 수량 추가 메서드
    public void StockPlus(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    // 재고 수정 메서드
    public void stockUpdate(Size size,  int stockQuantity) {
        this.size = size;
        this.stockQuantity = stockQuantity;
    }
}
