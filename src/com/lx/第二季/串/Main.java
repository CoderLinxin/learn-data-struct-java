package com.lx.第二季.串;

import com.lx.第二季.串.src.BruteForce01;
import com.lx.第二季.串.src.BruteForce02;
import com.lx.第二季.串.src.KMP;
import com.lx.第二季.串.test.Asserts;

public class Main {
    public static void main(String[] args) {
        Asserts.test(BruteForce01.indexOf1("hello world", "hello") == 0);
        Asserts.test(BruteForce01.indexOf1("hello world", "d") == 10);
        Asserts.test(BruteForce01.indexOf1("hello world", "world") == 6);

        Asserts.test(BruteForce01.indexOf2("hello world", "hello") == 0);
        Asserts.test(BruteForce01.indexOf2("hello world", "d") == 10);
        Asserts.test(BruteForce01.indexOf2("hello world", "world") == 6);

        Asserts.test(BruteForce02.indexOf("hello world", "hello") == 0);
        Asserts.test(BruteForce02.indexOf("hello world", "d") == 10);
        Asserts.test(BruteForce02.indexOf("hello world", "world") == 6);

        Asserts.test(KMP.indexOf("hello world", "hello") == 0);
        Asserts.test(KMP.indexOf("hello world", "d") == 10);
        Asserts.test(KMP.indexOf("hello world", "world") == 6);
    }
}
