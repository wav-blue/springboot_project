package kr.sujin.cafe.controller;

import kr.sujin.cafe.dto.DrinkFormDto;
import kr.sujin.cafe.dto.DrinkSearchDto;
import kr.sujin.cafe.entity.Drink;
import kr.sujin.cafe.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping(value = "/upload")
    public String drinkForm(Model model){
        model.addAttribute("drinkFormDto", new DrinkFormDto());
        return "item/drinkForm";
    }

    @PostMapping(value = "/upload")
    public String drinkNew(@Valid DrinkFormDto drinkFormDto, BindingResult
                           bindingResult, Model model, @RequestParam("drinkImgFile")List<MultipartFile>
                           drinkImgFileList){
        if(bindingResult.hasErrors()){
            return "item/drinkForm";
        }

        if(drinkImgFileList.get(0).isEmpty() && drinkFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 리뷰 이미지는 필수 입력 값입니다.");
            return "item/drinkForm";
        }

        try{
            drinkService.saveDrink(drinkFormDto, drinkImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/drinkForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/upload/{drinkId}")
    public String drinkDtl(@PathVariable("drinkId") Long drinkId, Model model){

        try{
            DrinkFormDto drinkFormDto = drinkService.getDrinkDtl(drinkId);
            model.addAttribute("drinkFormDto", drinkFormDto);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 리뷰입니다.");
            model.addAttribute("drinkFormDto", new DrinkFormDto());
            return "item/drinkForm";
        }
        return "item/drinkForm";
    }

    //수정을 위한 URL
    @PostMapping(value = "/upload/{drinkId}")
    public String drinkUpdate(@Valid DrinkFormDto drinkFormDto,
                              BindingResult bindingResult, @RequestParam("drinkImgFile") List<MultipartFile>
                              drinkImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/drinkForm";
        }
        if(drinkImgFileList.get(0).isEmpty() && drinkFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 음료 이미지는 필수 입력 값입니다.");
            return "item/drinkForm";
        }

        try{
            drinkService.updateItem(drinkFormDto, drinkImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "item/drinkForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/search", "/search/{page}"})
    public String drinkManage(DrinkSearchDto drinkSearchDto,
                              @PathVariable("page")Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Drink> drinks =
                drinkService.getAdminDrinkPage(drinkSearchDto, pageable);
        model.addAttribute("drinks", drinks);
        model.addAttribute("drinkSearchDto", drinkSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/drinkMng";
    }

    @GetMapping(value= "/item/{drinkId}")
    public String drinkDtl(Model model, @PathVariable("drinkId") Long drinkId){
        DrinkFormDto drinkFormDto = drinkService.getDrinkDtl(drinkId);
        model.addAttribute("drink", drinkFormDto);
        return "item/drinkDtl";
    }
}
