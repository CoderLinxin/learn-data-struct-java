package com.lx.线性表.栈;

import com.lx.线性表.栈.src.SequentialStack;

public class Main {
    public static void main(String[] args) {
        SequentialStack<Integer> sequentialStack = new SequentialStack<>();

        sequentialStack.push(11);
        sequentialStack.push(22);
        sequentialStack.push(33);
        sequentialStack.push(44);
        sequentialStack.push(55);

        while (!sequentialStack.isEmpty())
            System.out.println(sequentialStack.pop());
    }
}
