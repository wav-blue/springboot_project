package kr.sujin.cafe.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.sujin.cafe.constant.DrinkType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardDrinkDto {
    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String drinkDetail;

    private DrinkType drinkType;

    //private LocalDateTime regTime;

    @QueryProjection
    public BoardDrinkDto(Long id, String cafeNm, String menuNm, Integer rating, String drinkDetail, DrinkType drinkType, LocalDateTime regTime){
        this.id = id;
        this.cafeNm = cafeNm;
        this.menuNm = menuNm;
        this.rating = rating;
        this.drinkDetail = drinkDetail;
        this.drinkType = drinkType;
    }
}
