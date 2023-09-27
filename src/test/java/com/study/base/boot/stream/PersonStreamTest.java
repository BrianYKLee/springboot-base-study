package com.study.base.boot.stream;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PersonStreamTest {

    private List<Person> persons;

    @BeforeEach  // 테스트코드가 돌기 직전에 각각 한번씩 내부 메소드를 돌리는것
    void init() {
        persons = List.of(
                new Person(10, "십대"),
                new Person(20, "성인"),
                new Person(30, "어른"),
                new Person(40, "불혹")
        );
    }
    // 테스트를 돌리고 빌드 및 배포를 해서 에러를 미연에 방지
    @Nested
    public class 나이 {

        @Test
        void 나이가_30_이상인_사람_for() {
            List<Person> filterPerson = new ArrayList<>();

            for (int i = 0; i < persons.size(); i++) {
                final var person = persons.get(i);

                if (person.getAge() >= 30) {
                    filterPerson.add(person);
                }
            }

            assertAll(
                    () -> assertEquals(2, filterPerson.size())
            );
        }

        @Test
        void 나이가_30_이상인_사람_for_in() {

            List<Person> filterPerson = new ArrayList<>();

            for (Person person : persons) {

                if (person.getAge() >= 30) {
                    filterPerson.add(person);
                }
            }

            assertAll(
                    () -> assertEquals(2, filterPerson.size())
            );
        }

        @Test
        void 나이가_30_이상인_사람_stream() {
            final var filterPerson = persons.stream()
                    .filter(person -> person.getAge() >= 30)
                    .collect(Collectors.toList());

            assertAll(
                    () -> assertEquals(2, filterPerson.size())
            );
        }
    }


    @Nested
    public class 죽음 {

        @Test
        void 나이가_30_이상인_사람_죽음_for_in() {

            for (Person person : persons) {

                if (person.getAge() >= 30) {
                    person.endOfLife();
                }
            }

            assertAll(
                    () -> assertTrue(
                            persons.stream()
                                    .filter(person -> person.getAge() >= 30)
                                    .map(Person::isDead)
                                    .allMatch(dead -> dead)
                    )
            );
        }

        @Test
        void 나이가_30_이상인_사람_죽음_stream() {
            persons.stream()
                    .filter(person -> person.getAge() >= 30)
                    .forEach(person -> person.endOfLife());

            assertAll(
                    () -> assertTrue(
                            persons.stream()
                                    .filter(person -> person.getAge() >= 30)
                                    .map(Person::isDead)
                                    .allMatch(dead -> dead)
                    )
            );
        }
    }

    @Nested
    public class 정렬 {

        @Test
        void 나이_내림차순_stream() {
            final var sorted = persons.stream().sorted(Comparator.comparing(Person::getAge).reversed()).collect(Collectors.toList());

            assertAll(
                    () -> assertArrayEquals(
                            sorted.stream()
                                    .mapToInt(Person::getAge) // List<Integer>
                                    .toArray(),
                            new int[]{40, 30, 20, 10})
            );
        }
    }
}