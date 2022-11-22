package kr.sujin.cafe.entity;


import kr.sujin.cafe.constant.DrinkType;
import kr.sujin.cafe.dto.DrinkFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="drink")
@Getter
@Setter
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

    //private LocalDateTime regTime; // 등록 시간

    public void updateDrink(DrinkFormDto drinkFormDto){
        this.cafeNm = drinkFormDto.getCafeNm();
        this.menuNm = drinkFormDto.getMenuNm();
        this.rating = drinkFormDto.getRating();
        this.drinkDetail = drinkFormDto.getDrinkDetail();
        this.drinkType = drinkFormDto.getDrinkType();
        //this.regTime = drinkFormDto.getRegTime();
    }
}