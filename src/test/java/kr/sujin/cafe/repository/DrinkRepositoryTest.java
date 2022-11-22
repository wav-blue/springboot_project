package kr.sujin.cafe.repository;

import kr.sujin.cafe.constant.DrinkType;
import kr.sujin.cafe.entity.Drink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest // 테스트 실행시 모든 Bean을 IoC 컨테이너에 등록 = 스프링부트 구동과 동일한 환경 조건
@TestPropertySource(locations="classpath:application-test.properties") // 테스트 실행시 우선되는 설정파일
class DrinkRepositoryTest {
    @Autowired // 필드 Bean 주입
    DrinkRepository drinkRepository;

    @Test // Method 테스트 대상 지정
    @DisplayName("게시글 저장 확인") // 테스트명
    public void createDrinkTest(){
        Drink drink = new Drink();
        drink.setId(1L);
        drink.setCafeNm("카페 이름1");
        drink.setMenuNm("메뉴 이름1");
        drink.setRating(5);
        drink.setDrinkDetail("맛있습니다.");
        drink.setDrinkType(DrinkType.ETC);
        drink.setRegTime(LocalDateTime.now());
        Drink savedDrink = drinkRepository.save(drink);
        System.out.println(savedDrink.toString());
    }
}