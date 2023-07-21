package kr.sujin.cafe.service;

import kr.sujin.cafe.dto.DrinkFormDto;
import kr.sujin.cafe.dto.DrinkImgDto;
import kr.sujin.cafe.dto.DrinkSearchDto;
import kr.sujin.cafe.dto.ExploreDrinkDto;
import kr.sujin.cafe.entity.Drink;
import kr.sujin.cafe.entity.DrinkImg;
import kr.sujin.cafe.repository.DrinkImgRepository;
import kr.sujin.cafe.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final DrinkImgService drinkImgService;
    private final DrinkImgRepository drinkImgRepository;

    public Long saveDrink(DrinkFormDto drinkFormDto,
                             List<MultipartFile> drinkImgFileList) throws Exception{
        //상품 등록
        Drink drink = drinkFormDto.createItem();
        drinkRepository.save(drink);

        //이미지 등록
        for(int i = 0; i < drinkImgFileList.size(); i++){
            DrinkImg drinkImg = new DrinkImg();
            drinkImg.setDrink(drink);
            if(i==0) drinkImg.setRepimgYn("Y"); //첫번째 이미지는 대표 이미지 값을 Y로 설정
            else drinkImg.setRepimgYn("N");
            drinkImgService.saveDrinkImg(drinkImg, drinkImgFileList.get(i));
        }
        return drink.getId();
    }

    @Transactional(readOnly = true)
    public DrinkFormDto getDrinkDtl(Long drinkId){

        List<DrinkImg> drinkImgList = drinkImgRepository.findByDrinkIdOrderByIdAsc(drinkId);
        List<DrinkImgDto> drinkImgDtoList = new ArrayList<>();
        for(DrinkImg drinkImg : drinkImgList){
            DrinkImgDto drinkImgDto = DrinkImgDto.of(drinkImg);
            drinkImgDtoList.add(drinkImgDto);
        }

        Drink drink = drinkRepository.findById(drinkId).orElseThrow(EntityNotFoundException::new);
        DrinkFormDto drinkFormDto = DrinkFormDto.of(drink);
        drinkFormDto.setDrinkImgDtoList(drinkImgDtoList);
        return drinkFormDto;
    }

    //정보 업데이트
    public Long updateItem(DrinkFormDto drinkFormDto, List<MultipartFile> drinkImgFileList) throws Exception{
        //수정
        Drink drink = drinkRepository.findById(drinkFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        drink.updateDrink(drinkFormDto);

        List<Long> drinkImgIds = drinkFormDto.getDrinkImgIds();
        //이미지 등록
        for(int i = 0; i < drinkImgFileList.size(); i++){
            drinkImgService.updateDrinkImg(drinkImgIds.get(i), drinkImgFileList.get(i));
        }

        return drink.getId();
    }
    @Transactional(readOnly = true) // 데이터의 수정이 일어나지 않음 -> 최적화를 위한 어노테이션
    public Page<Drink> getAdminDrinkPage(DrinkSearchDto drinkSearchDto, Pageable pageable){
        return drinkRepository.getAdminDrinkPage(drinkSearchDto, pageable);
    }

    //Explore
    @Transactional(readOnly = true)
    public Page<ExploreDrinkDto> getExploreDrinkPage(DrinkSearchDto drinkSearchDto, Pageable pageable){
        return drinkRepository.getExploreDrinkPage(drinkSearchDto, pageable);
    }
}
