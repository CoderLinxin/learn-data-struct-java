package com.lx.哈希表;

import java.util.Objects;

public class Main {
    // 计算字符串的哈希值
    public static void getHash() {
        String str = "abcdefg";
        int hashCode = 0;

        // a*31^6 + b*31^5 + c*31^4 + ...
        // = ((a*31 + b)*31 + c)*31 + ...
        for (int i = 0; i < str.length(); i++) {
            char letter = str.charAt(i);
            hashCode = hashCode * 31 + letter; // 会倍 jvm 优化成下面的等价形式
//            hashCode = (hashCode << 5) - hashCode + letter;
        }

        System.out.println(hashCode); // -1206291356
    }

    // 获取自定义对象的哈希值
    public static void getHashWithObj() {
        class Student {
            public Student(String name) {
                this.name = name;
            }

            final String name;
        }

        class Person {
            final String name;
            final String sex;
            final int age;

            public Person(String name, String sex, int age) {
                this.name = name;
                this.sex = sex;
                this.age = age;
            }

            // 开发中使用自定义对象作为哈希表的 key 时也一般都需要重写该方法
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Person person = (Person) o;

                return this.age == person.age
                        && (this.sex == null ? person.sex == null : this.sex.equals(person.sex))
                        && (this.name == null ? person.name == null : this.name.equals(person.name));
            }

            // 开发中使用自定义对象作为哈希表的 key 时一般都需要重写该方法(否则生成的哈希表可能会不稳定)
            @Override
            public int hashCode() {
                int nameHash = this.name.hashCode(); // String 默认提供了计算 hash 的方法
                int sexHash = this.sex.hashCode();

                // 哈希值大太造成整型溢出怎么办？
                //  不需要做任何处理(因为我们的目的只是为了获取一个确定的整型值)
                return (nameHash * 31 + sexHash) * 31 + this.age; // 将每个成员变量当成字符串中的每一个字符(按照计算字符串哈希值的方式来计算)
            }
        }

        Person person1 = new Person("张三", "男", 18);
        Person person2 = new Person("张三", "男", 18);
        Student student1 = new Student("李四");
        Student student2 = new Student("王五");

        System.out.println(person1.hashCode() == person2.hashCode()); // true
        System.out.println(student1.hashCode() == student2.hashCode()); // false，默认自定义对象的哈希值计算与其内存地址有关(对象内存地址不同则哈希值不同)
    }

    public static void main(String[] args) {
//        getHash();
        getHashWithObj();
    }

}
