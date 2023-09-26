package com.study.base.boot.stream;

public class Person {

    private int age;
    private String name;
    private boolean isDead;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public boolean isDead() {
        return isDead;
    }

    public void endOfLife() {
        this.isDead = true;
    }
}