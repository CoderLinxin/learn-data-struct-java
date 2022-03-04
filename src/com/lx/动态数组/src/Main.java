package com.lx.动态数组.src;

class Person {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else
            return age == ((Person) o).age;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(this.toString() + " 被回收了~");
    }
}

public class Main {
    public static void main(String[] args) {
//        MyArrayList<Integer> myArrayList = new MyArrayList<>();
//        myArrayList.add(10);
//        myArrayList.add(30);
//        myArrayList.add(40);
//        myArrayList.add(45);
//        myArrayList.add(99);

//        myArrayList.set(3,999);
//        System.out.println(myArrayList.indexOf(40));
//        System.out.println(myArrayList.isEmpty());
//        myArrayList.clear();
//        System.out.println(myArrayList.contains(40));
//        System.out.println(myArrayList.get(0));

        MyArrayList<Person> myArrayList = new MyArrayList<>();
        myArrayList.add(new Person("张三", 44));
        myArrayList.add(new Person("李四", 20));
        myArrayList.add(new Person("王五", 18));
        myArrayList.add(new Person("赵六", 40));
//        System.out.println(myArrayList.indexOf(new Person("老王", 18)));
//        myArrayList.clear();
//        System.gc();
        myArrayList.traver();
    }
}
