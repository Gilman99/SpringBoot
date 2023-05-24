package com.example.Practice1.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String brand;
    private String name;
    private Integer price;
    private Integer stock;

    /*
     * 비즈니스 모델 (실기능) 추가
     * */
    @Comment("재고 추가")
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    @Comment("재고 감소")
    public void remoteStock(int stockQuantity) {
        int restStock = this.stock - stockQuantity;
        if (restStock < 0) {
            throw new IllegalStateException("남은 수량과 삭제 수량을 확인해주세요.");
        }
        this.stock = restStock;
    }


}