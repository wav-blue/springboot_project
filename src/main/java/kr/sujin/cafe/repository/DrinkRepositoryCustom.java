package kr.sujin.cafe.repository;

import kr.sujin.cafe.dto.DrinkSearchDto;
import kr.sujin.cafe.dto.ExploreDrinkDto;
import kr.sujin.cafe.entity.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DrinkRepositoryCustom {
    Page<Drink> getAdminDrinkPage(DrinkSearchDto drinkSearchDto, Pageable pageable);

    Page<ExploreDrinkDto> getExploreDrinkPage(DrinkSearchDto drinkSearchDto, Pageable pageable);
}
