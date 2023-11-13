package kr.sujin.cafereview.dto;

import kr.sujin.cafereview.constant.DrinkType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DrinkDto {

    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String drinkDetail;

    private DrinkType drinkType;

}

