package kr.sujin.cafe.service;

import kr.sujin.cafe.constant.DrinkType;
import kr.sujin.cafe.dto.DrinkFormDto;
import kr.sujin.cafe.entity.Drink;
import kr.sujin.cafe.entity.DrinkImg;
import kr.sujin.cafe.repository.DrinkImgRepository;
import kr.sujin.cafe.repository.DrinkRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class DrinkServiceTest {
    @Autowired
    DrinkService drinkService;

    @Autowired
    DrinkRepository drinkRepository;

    @Autowired
    DrinkImgRepository drinkImgRepository;

    List<MultipartFile> creaMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "C:/cafe/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile=
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void saveDrink() throws Exception{
        DrinkFormDto drinkFormDto = new DrinkFormDto();
        drinkFormDto.setCafeNm("카페이름 1");
        drinkFormDto.setMenuNm("아메리카노");
        drinkFormDto.setDrinkType(DrinkType.COFFEE);
        drinkFormDto.setRating(5);
        drinkFormDto.setDrinkDetail("맛있어요.");

        List<MultipartFile> multipartFileList = creaMultipartFiles();
        Long drinkId = drinkService.saveDrink(drinkFormDto, multipartFileList);

        List<DrinkImg> drinkImgList = drinkImgRepository.findByDrinkIdOrderByIdAsc(drinkId);
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(EntityNotFoundException::new);

        assertEquals(drinkFormDto.getCafeNm(), drink.getCafeNm());
        assertEquals(drinkFormDto.getMenuNm(), drink.getMenuNm());
        assertEquals(drinkFormDto.getDrinkType(), drink.getDrinkType());
        assertEquals(drinkFormDto.getRating(), drink.getRating());
        assertEquals(drinkFormDto.getDrinkDetail(), drink.getDrinkDetail());
        assertEquals(multipartFileList.get(0).getOriginalFilename(),drinkImgList.get(0).getOriImgName());
    }
}