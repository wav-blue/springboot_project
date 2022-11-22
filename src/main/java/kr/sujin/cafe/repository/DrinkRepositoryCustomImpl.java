package kr.sujin.cafe.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.sujin.cafe.constant.DrinkType;
import kr.sujin.cafe.dto.DrinkSearchDto;

import kr.sujin.cafe.dto.ExploreDrinkDto;
import kr.sujin.cafe.dto.QExploreDrinkDto;
import kr.sujin.cafe.entity.Drink;
import kr.sujin.cafe.entity.QDrink;

import kr.sujin.cafe.entity.QDrinkImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class DrinkRepositoryCustomImpl implements DrinkRepositoryCustom{
        private JPAQueryFactory queryFactory;

        public DrinkRepositoryCustomImpl(EntityManager em){
            this.queryFactory = new JPAQueryFactory(em);
        }

        private BooleanExpression searchDrinkTypeEq(DrinkType searchDrinkType){
            return searchDrinkType ==
                    null? null : QDrink.drink.drinkType.eq(searchDrinkType);
        }

        private BooleanExpression searchByLike(String searchBy, String searchQuery){
            if(StringUtils.equals("menuNm", searchBy)){
                return QDrink.drink.menuNm.like("%" + searchQuery +"%");
            }else if(StringUtils.equals("cafeNm", searchBy)){
                return QDrink.drink.cafeNm.like("%" + searchQuery+"%");
            }else if(StringUtils.equals("createdBy", searchBy)){
                return QDrink.drink.createdBy.like("%" + searchQuery+"%");
            }

            return null;
            }

            @Override
    public Page<Drink> getAdminDrinkPage(DrinkSearchDto drinkSearchDto, Pageable pageable){
                QueryResults<Drink> results = queryFactory
                        .selectFrom(QDrink.drink)
                        .where(searchDrinkTypeEq(drinkSearchDto.getSearchDrinkType()),
                                searchByLike(drinkSearchDto.getSearchBy(),
                                        drinkSearchDto.getSearchQuery()))
                        .orderBy(QDrink.drink.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

                List<Drink> content = results.getResults();
                long total = results.getTotal();
                return new PageImpl<>(content, pageable, total);
        }

        public BooleanExpression drinkNmLike(String searchQuery){
            return StringUtils.isEmpty(searchQuery)?
                    null : QDrink.drink.menuNm.like("%" + searchQuery + "%");
        }

        @Override
        public Page<ExploreDrinkDto> getExploreDrinkPage(DrinkSearchDto drinkSearchDto, Pageable pageable){
            QDrink drink = QDrink.drink;
            QDrinkImg drinkImg = QDrinkImg.drinkImg;

            QueryResults<ExploreDrinkDto> results = queryFactory
                    .select(
                            new QExploreDrinkDto(
                                drink.id,
                                drink.cafeNm,
                                drink.menuNm,
                                drink.rating,
                                drink.drinkDetail,
                                drink.drinkType,
                                drinkImg.imgUrl)
                    )
                    .from(drinkImg)
                    .join(drinkImg.drink, drink)
                    .where(drinkImg.repimgYn.eq("Y"))
                    .where(drinkNmLike(drinkSearchDto.getSearchQuery()))
                    .orderBy(drink.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();
            List<ExploreDrinkDto> content = results.getResults();
            long total = results.getTotal();
            return new PageImpl<>(content, pageable, total);
        }
}
