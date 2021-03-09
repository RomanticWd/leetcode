package com.leetcode.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MessageExecutor implements Callable {

    /**
     * 池中所保存的线程数
     */
    private static final int corePoolSize = 3;
    /**
     * 池中允许的最大线程数
     */
    private static final int maximumPoolSize = 10;

    /**
     * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
     */
    private static final long keepAliveTime = 3;

    /**
     * 线程池对象
     */
    private static ThreadPoolExecutor threadPool = null;

    private String name;

    private int age;

    private static Message message = new Message();

    static {
        // 初始化线程池
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(50), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public MessageExecutor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void sendMessage(String name, int age) {
        threadPool.submit(new MessageExecutor(name, age));
    }

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "-----------准备调用业务方法");
        System.out.println("messgae对象" + message);
        return message.process(name, age);
    }
}
