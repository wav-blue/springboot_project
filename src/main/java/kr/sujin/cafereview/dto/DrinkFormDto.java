package kr.sujin.cafereview.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

//리뷰 데이터 정보를 전달

import kr.sujin.cafereview.constant.DrinkType;
import kr.sujin.cafereview.entity.Drink;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DrinkFormDto {
    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String drinkDetail;

    private DrinkType drinkType;

    //음료를 저장한 후 수정할 때 이미지 정보를 저장하는 리스트
    private List<DrinkImgDto> drinkImgDtoList = new ArrayList<>();

    private List<Long> drinkImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Drink createItem(){
        return modelMapper.map(this, Drink.class);
    }

    public static DrinkFormDto of(Drink drink) {return modelMapper.map(drink, DrinkFormDto.class);}
}
