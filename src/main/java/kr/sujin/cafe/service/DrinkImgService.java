package kr.sujin.cafe.service;

import kr.sujin.cafe.entity.DrinkImg;
import kr.sujin.cafe.repository.DrinkImgRepository;
import lombok.RequiredArgsConstructor;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class DrinkImgService {
    @Value("${drinkImgLocation}")
    private String drinkImgLocation;

    private final DrinkImgRepository drinkImgRepository;

    private final FileService fileService;

    public void saveDrinkImg(DrinkImg drinkImg, MultipartFile drinkImgFile) throws Exception{
        String oriImgName = drinkImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(drinkImgLocation, oriImgName,
                    drinkImgFile.getBytes());
            imgUrl = "/images/img/" + imgName;
        }
        
        //상품 이미지 정보 저장
        drinkImg.updateDrinkImg(oriImgName, imgName, imgUrl);
        drinkImgRepository.save(drinkImg);
    }

    public void updateDrinkImg(Long drinkImgId, MultipartFile drinkImgFile) throws Exception{
        if(!drinkImgFile.isEmpty()){
            DrinkImg savedDrinkImg = drinkImgRepository.findById(drinkImgId)
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedDrinkImg.getImgName())){
                fileService.deleteFile(drinkImgLocation+"/"+savedDrinkImg.getImgName());
            }

            String oriImgName = drinkImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(drinkImgLocation,
                    oriImgName, drinkImgFile.getBytes());
            String imgUrl = "/images/img/" + imgName;
            savedDrinkImg.updateDrinkImg(oriImgName, imgName, imgUrl);
        }
    }
}
