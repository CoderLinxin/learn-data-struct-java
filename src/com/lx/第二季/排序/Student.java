package com.lx.第二季.排序;

/**
 * 仅用于测试排序算法是否稳定的类
 */
public class Student implements Comparable<Student> {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int age;
    private int score;

    @Override
    public int compareTo(Student o) {
        return this.age - o.age;
    }
}
