package kr.sujin.cafe.dto;

//리뷰 데이터 정보를 전달

import kr.sujin.cafe.constant.DrinkType;
import kr.sujin.cafe.entity.Drink;
import lombok.Getter;
import lombok.Setter;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
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
