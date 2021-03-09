package com.leetcode.thread;

public class Message {

    public String process(String name, int age) {
        System.out.println(Thread.currentThread().getName() + ":name----" + name + "，age----" + age);
        return "success";
    }

}
