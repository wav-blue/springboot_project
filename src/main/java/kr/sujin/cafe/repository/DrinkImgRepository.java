package kr.sujin.cafe.repository;

import kr.sujin.cafe.entity.DrinkImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkImgRepository extends JpaRepository<DrinkImg, Long> {

    List<DrinkImg> findByDrinkIdOrderByIdAsc(Long drinkId);

}
