package com.study.base.boot.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class StreamTest {

    class User {
        private int age;
        private String name;
        private boolean isDead;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public void newYear() {
            this.age++;
        }

        public int getAge() {
            return this.age;
        }

        public String getName() {
            return this.name;
        }

        public boolean isDead() {
            return this.isDead;
        }

        public void dead() {
            this.isDead = true;
        }

        public void endOfLife() {

            if (this.age >= 30) {
                this.isDead = true;
            }
        }
    }

    private List<User> users;

    @BeforeEach
    void before() {
        this.users = List.of(
                new User(10, "꼬마"),
                new User(20, "성인"),
                new User(30, "직장인"),
                new User(40, "불혹")
        );
    }

    @Test
    void 나이가_30_이상인_사람_for() {
        List<User> filterUsers = new ArrayList<>();

        final var size = this.users.size();

        for (int i = 0; i < size; i++) {
            final var user = this.users.get(i);

            if (user.getAge() >= 30) {
                filterUsers.add(user);
            }
        }

        assertAll(
                () -> assertEquals(2, filterUsers.size())
        );
    }

    @Test
    void 나이가_30_이상인_사람_for_in() {
        List<User> filterUsers = new ArrayList<>();

        for (User user : this.users) {
            if (user.getAge() >= 30) {
                filterUsers.add(user);
            }
        }

        assertAll(
                () -> assertEquals(2, filterUsers.size())
        );
    }

    @Test
    void 나이가_30_이상인_사람_stream() {
        final var filterUsers = this.users.stream()
                .filter(user -> user.getAge() >= 30)
                .collect(Collectors.toList());

        assertAll(
                () -> assertEquals(2, filterUsers.size())
        );
    }

    @Test
    void 나이가_30_이상인_사람_죽음_for_in() {

        for (User user : this.users) {

            // TODO: 2023/09/09 추상화 레벨로 묶기
            if (user.getAge() >= 30) {
                user.dead();
            }
        }

        assertAll(
                () -> assertTrue(
                        this.users.stream()
                                .filter(user -> user.getAge() >= 30)
                                .map(User::isDead)
                                .allMatch(dead -> dead)
                )
        );
    }

    @Test
    void 나이가_30_이상인_사람_죽음_stream() {
        this.users.stream()
                .filter(user -> user.getAge() >= 30)
                .forEach(User::dead);

        assertAll(
                () -> assertTrue(
                        this.users.stream()
                                .filter(user -> user.getAge() >= 30)
                                .map(User::isDead)
                                .allMatch(dead -> dead)
                )
        );
    }

    @Test
    void 나이_내림차순_stream() {
        final var sortedUsers = this.users.stream()
                .sorted(Comparator.comparing(User::getAge).reversed())
                .collect(Collectors.toList());

        assertAll(
                () -> assertArrayEquals(
                        sortedUsers.stream()
                                .mapToInt(User::getAge)
                                .toArray(),
                        new int[] {40,30,20,10})
        );
    }
}