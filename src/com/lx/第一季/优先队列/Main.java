package com.lx.第一季.优先队列;

import com.lx.第一季.优先队列.src.PriorityQueue;

public class Main {
    static class Person implements Comparable {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Object o) {
            return this.age - ((Person) o).age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>();

        priorityQueue.enQueue(new Person("张三", 15));
        priorityQueue.enQueue(new Person("李四", 50));
        priorityQueue.enQueue(new Person("王五", 20));
        priorityQueue.enQueue(new Person("赵六", 2));
        priorityQueue.enQueue(new Person("老王", 99));

        while (!priorityQueue.isEmpty())
            System.out.println(priorityQueue.deQueue());
    }
}
