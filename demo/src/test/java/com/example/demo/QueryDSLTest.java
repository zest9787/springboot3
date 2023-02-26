package com.example.demo;

import com.example.demo.feature.foodstore.dto.FoodStoreDTO;
import com.example.demo.feature.foodstore.dto.QFoodStoreDTO;
import com.example.demo.feature.foodstore.entity.FoodStore;
import com.example.demo.feature.foodstore.entity.FoodType;
import com.example.demo.feature.foodstore.repository.FoodStoreRepository;
import com.example.demo.feature.foodstore.repository.FoodTypeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static com.example.demo.feature.foodstore.entity.QFoodStore.foodStore;
import static com.example.demo.feature.foodstore.entity.QFoodType.foodType;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QueryDSLTest {

    @Autowired
    FoodTypeRepository foodTypeRepository;
    @Autowired
    FoodStoreRepository foodStoreRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Before
    public void setData() {
        FoodType korean = new FoodType("한식", 1);
        FoodType western = new FoodType("양식", 2);
        FoodType chinese = new FoodType("중식", 3);

        foodTypeRepository.saveAll(List.of(korean, western, chinese));

        FoodStore foodStore1 = new FoodStore("삼겹살", 9, "sangmessi", korean);
        FoodStore foodStore2 = new FoodStore("닭갈비", 2, "sangmessi", korean);
        FoodStore foodStore3 = new FoodStore("부대찌개", 3, "lake", korean);
        FoodStore foodStore4 = new FoodStore("순대국밥", 4, "lake", korean);
        FoodStore foodStore5 = new FoodStore("소고기", 5, "lake", korean);
        FoodStore foodStore6 = new FoodStore("스파게티", 6, "sangmessi", western);
        FoodStore foodStore7 = new FoodStore("피자", 7, "sangmessi", western);
        FoodStore foodStore8 = new FoodStore("중국집", 8, "hong", chinese);
        FoodStore foodStore9 = new FoodStore("중국집2", 9, "hong", chinese);
        FoodStore foodStore10 = new FoodStore("중국집3", 10, "hong", chinese);

        foodStoreRepository.saveAll(List.of(foodStore1, foodStore2, foodStore3, foodStore4, foodStore5, foodStore6, foodStore6, foodStore7, foodStore8, foodStore9, foodStore10));
    }

    @Test
    public void 기본쿼리() {
        List<FoodStore> list = jpaQueryFactory.selectFrom(foodStore)
                .fetch();
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    public void 기본쿼리_조건절() {
        List<FoodStore> list = jpaQueryFactory.selectFrom(foodStore)
                .where(foodStore.rate.goe(5))
                .fetch();
        assertThat(list.size()).isEqualTo(7);
    }

    @Test
    public void 기본쿼리_조건절2() {
        List<FoodStore> list = jpaQueryFactory.selectFrom(foodStore)
                .where(foodStore.rate.goe(5),
                        foodStore.storeName.startsWith("삼"))
                .fetch();
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void 기본쿼리_정렬() {
        List<FoodStore> list = jpaQueryFactory.selectFrom(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch();
        assertThat(list.size()).isEqualTo(10);
        assertThat(list.get(0).getRate()).isEqualTo(10);
    }

    @Test
    public void 기본쿼리_페이징() {
        Pageable pageable = PageRequest.of(0, 3);
        List<FoodStore> list = jpaQueryFactory.selectFrom(foodStore)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        assertThat(list.size()).isEqualTo(10);
        assertThat(new PageImpl<>(list, pageable, list.size()).getSize()).isEqualTo(3);
//        assertThat(list.get(0).getRate()).isEqualTo(3);
    }

    @Test
    public void 기본쿼리_조인() {
        List<FoodStore> list = jpaQueryFactory.selectFrom(foodStore)
                .join(foodStore.foodType, foodType)
                .fetch();
        list.forEach(System.out::println);
    }

    @Test
    public void 기본쿼리_연관관계없는_조인() {
        List<FoodStore> list = jpaQueryFactory.select(foodStore)
                .from(foodStore, foodType)
                .where(foodStore.rate.eq(foodType.foodOrder))
                .fetch();
        list.forEach(System.out::println);
    }

    @Test
    public void 기본쿼리_조인_on() {
        List<FoodStore> list = jpaQueryFactory.select(foodStore)
                .from(foodStore)
                .join(foodType).on(foodType.foodOrder.eq(foodStore.rate))
                .fetch();
        list.forEach(System.out::println);
    }

    @Test
    public void 기본쿼리_left조인() {
        List<Tuple> list = jpaQueryFactory.select(foodStore, foodType)
                .from(foodStore)
                .leftJoin(foodType).on(foodType.foodOrder.eq(foodStore.rate))
                .fetch();
        list.forEach(System.out::println);
    }

    @Test
    public void 기본쿼리_subquery() {
        List<FoodStore> list = jpaQueryFactory.select(foodStore)
                .where(foodStore.rate.in(
                        JPAExpressions
                                .select(foodType.foodOrder.max())
                                .from(foodType)
                ))
                .fetch();
        list.forEach(System.out::println);
    }


    @Test
    public void 기본쿼리_case() {
        List<String> list = jpaQueryFactory
                .select(
                        foodStore.rate
                                .when(10).then("존맛탱")
                                .when(9).then("맛남")
                                .otherwise("그럭저럭"))
                .from(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch();
        List<String> list2 = jpaQueryFactory
                .select(
                        new CaseBuilder()
                                .when(foodStore.rate.goe(7)).then("존맛탱")
                                .when(foodStore.rate.goe(4)).then("맛남")
                                .otherwise("그럭저럭"))
                .from(foodStore)
                .orderBy(foodStore.rate.desc())
                .fetch();
        list.forEach(System.out::println);
    }


    @Test
    public void 기본쿼리_custom() {
        List<FoodStoreDTO> list = jpaQueryFactory
                .select(new QFoodStoreDTO(
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder
                ))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .fetch();
        list.forEach(System.out::println);
    }


    @Test
    public void 동적쿼리_custom() {
        List<FoodStoreDTO> 삼겹살 = dynamicQuery("삼", 0);
        List<FoodStoreDTO> lessThanFive = dynamicQuery("대", 5);
        삼겹살.forEach(System.out::println);
        lessThanFive.forEach(System.out::println);
    }

    private List<FoodStoreDTO> dynamicQuery(String storeName, int rate) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!Objects.isNull(storeName)) {
            builder.and(foodStore.storeName.contains(storeName));
        } else {
            builder.and(foodStore.rate.loe(rate));
        }
        List<FoodStoreDTO> list = jpaQueryFactory
                .select(new QFoodStoreDTO(
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder
                ))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .where(builder)
                .fetch();
        list.forEach(System.out::println);
        return list;
    }

    @Test
    public void 동적쿼리_custom2() {
        List<FoodStoreDTO> 삼겹살 = dynamicQuery2("삼", 0);
        List<FoodStoreDTO> lessThanFive = dynamicQuery2("대", 5);
        삼겹살.forEach(System.out::println);
        lessThanFive.forEach(System.out::println);
    }

    private List<FoodStoreDTO> dynamicQuery2(String storeName, int rate) {

        List<FoodStoreDTO> list = jpaQueryFactory
                .select(new QFoodStoreDTO(
                        foodStore.storeName,
                        foodStore.rate,
                        foodStore.ownerName,
                        foodType.foodTypeName,
                        foodType.foodOrder
                ))
                .from(foodStore)
                .join(foodStore.foodType, foodType)
                .where(storeNameContains(storeName),
                        lessThan(5))
                .fetch();
        list.forEach(System.out::println);
        return list;
    }

    private BooleanExpression storeNameContains(String storeName) {
        return Objects.isNull(storeName) ? null : foodStore.storeName.contains(storeName);
    }

    private BooleanExpression lessThan(int rate) {
        return rate <= 0 ? null : foodStore.rate.loe(rate);
    }


    @Test
    public void 집합() {
        List<Tuple> fetch = jpaQueryFactory
                .select(
                        foodStore.rate.max(),
                        foodStore.rate.avg(),
                        foodStore.rate.sum()
                )
                .from(foodStore)
                .fetch();
        Tuple tuple = fetch.get(0);
        System.out.println("tuple = " + tuple);
    }
}
