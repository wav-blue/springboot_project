package kr.sujin.cafereview.entity;


import javax.persistence.*;

import kr.sujin.cafereview.constant.DrinkType;
import kr.sujin.cafereview.dto.DrinkFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@Table(name="drink")
@ToString
public class Drink extends BaseEntity{
    @Id
    @Column(name="drinkId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //글 등록 코드

    @Column(nullable = false, length = 20)
    private String cafeNm; //카페이름

    @Column(nullable = false, length = 20)
    private String menuNm; //메뉴이름

    @Column(nullable = false)
    private Integer rating; //추천도

    @Lob
    @Column(nullable = false)
    private String drinkDetail; //세부 감상

    @Enumerated(EnumType.STRING)
    private DrinkType drinkType; // 메뉴 종류

    public void updateDrink(DrinkFormDto drinkFormDto){
        this.cafeNm = drinkFormDto.getCafeNm();
        this.menuNm = drinkFormDto.getMenuNm();
        this.rating = drinkFormDto.getRating();
        this.drinkDetail = drinkFormDto.getDrinkDetail();
        this.drinkType = drinkFormDto.getDrinkType();
    }
}