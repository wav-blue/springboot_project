package kr.sujin.cafe.controller;

import groovy.util.logging.Slf4j;
import kr.sujin.cafe.dto.DrinkFormDto;
import kr.sujin.cafe.dto.DrinkSearchDto;
import kr.sujin.cafe.dto.ExploreDrinkDto;
import kr.sujin.cafe.entity.Drink;
import kr.sujin.cafe.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j

@Controller
@RequiredArgsConstructor
public class MainController {

    private final DrinkService drinkService;

    @GetMapping("/")
    public String index(Model model){
        return "main";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "info/about";
    }
    @GetMapping(value = "/explore")
    public String explore(DrinkSearchDto drinkSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<ExploreDrinkDto> drinks = drinkService.getExploreDrinkPage(drinkSearchDto, pageable);
        model.addAttribute("drinks", drinks);
        model.addAttribute("drinkSearchDto", drinkSearchDto);
        model.addAttribute("maxPage", 3);
        return "post/explore";
    }

}
