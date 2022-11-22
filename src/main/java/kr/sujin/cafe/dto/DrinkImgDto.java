package kr.sujin.cafe.dto;

import kr.sujin.cafe.entity.DrinkImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

//음료 이미지에 대한 데이터를 전달
@Getter @Setter
public class DrinkImgDto {

    private static ModelMapper modelMapper = new ModelMapper();

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;


    public static DrinkImgDto of(DrinkImg drinkImg){
        return modelMapper.map(drinkImg, DrinkImgDto.class);
    }
}
