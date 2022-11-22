package kr.sujin.cafe.dto;

import kr.sujin.cafe.constant.DrinkType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DrinkSearchDto {
    private DrinkType searchDrinkType;

    private String searchBy;

    private String searchQuery="";
}
