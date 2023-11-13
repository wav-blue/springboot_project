package kr.sujin.cafereview.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.sujin.cafereview.constant.DrinkType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExploreDrinkDto {
    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String drinkDetail;

    private DrinkType drinkType;

    private String imgUrl;

    @QueryProjection
    public ExploreDrinkDto(Long id, String cafeNm, String menuNm, Integer rating, String drinkDetail, DrinkType drinkType, String imgUrl){
        this.id = id;
        this.cafeNm = cafeNm;
        this.menuNm = menuNm;
        this.rating = rating;
        this.drinkDetail = drinkDetail;
        this.drinkType = drinkType;
        this.imgUrl = imgUrl;
    }
}
