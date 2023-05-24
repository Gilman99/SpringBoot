package com.example.springGilman.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

/*엔티티 추가하고 기존 파일에 Add Maven artifact 통해 Dependencies 자카르타 추가하려다 실패...파일 처음부터 다시 만들었다
* 나중에 어떻게 해야하는건지 물어봐야지, 범인은 thymeleaf였다
* 추가로 생각해 보면, 너무 신경쓰지 말 것 그랬다 짜피 아이템 컨트롤러 도메인 부분만 보는거라...그래도 다음에 할때 제대로 돌아가겠지?*/
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItem = new ArrayList<>();

    private String brand;
    private String name;
    private Integer price;
    private Integer stock;

    /**
     * 음 뭘 추가할까 브랜드, 상품명, 가격, 수량, submit 버튼
     * sql?
     */
    @Comment("재고 추가")
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    @Comment("재고 감소")
    public void removeStock(int stockQuantity) {
        int restStock = this.stock - stockQuantity;
        if (restStock < 0) {
            throw new IllegalStateException("여분, 삭제 수량 체크");
        }
        this.stock = restStock;
    }
}
