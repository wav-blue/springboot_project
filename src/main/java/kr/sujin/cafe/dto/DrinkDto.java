package kr.sujin.cafe.dto;

import kr.sujin.cafe.constant.DrinkType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DrinkDto {

    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String drinkDetail;

    private DrinkType drinkType;

    //private LocalDateTime regTime;

}

