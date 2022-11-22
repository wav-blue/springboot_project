package kr.sujin.cafe.repository;

import kr.sujin.cafe.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DrinkRepository extends JpaRepository<Drink, Long>, QuerydslPredicateExecutor<Drink>,
DrinkRepositoryCustom{
}
