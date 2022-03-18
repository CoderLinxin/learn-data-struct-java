package com.lx.第一季.映射;

import com.lx.第一季.映射.src.LinkedListMap;

public class Main {
    public static void test1() {
        LinkedListMap<String, String> map = new LinkedListMap<>();

        String[] keys = new String[]{"name", "age", "sex"};
        String[] values = new String[]{"姓名", "年龄", "性别"};

        for (int i = 0; i < keys.length; i++)
            map.put(keys[i], values[i]);

//        System.out.println(map.remove("name"));
        System.out.println(map.put("name", "张三"));

//        System.out.println(map.get("name"));
//        System.out.println(map.get("age"));
//        System.out.println(map.get("sex"));

//        System.out.println(map.containsKey("name"));
//        System.out.println(map.containsKey("name1"));
//        System.out.println(map.containsValue("张三"));
//        System.out.println(map.containsValue("李四"));

        map.traversal(new LinkedListMap.Visitor<String, String>() {
            @Override
            public boolean visit(String key, String value) {
                System.out.println("key: " + key + " value: " + value);
                return false;
            }
        });
    }

    public static void main(String[] args) {
        test1();
    }
}
