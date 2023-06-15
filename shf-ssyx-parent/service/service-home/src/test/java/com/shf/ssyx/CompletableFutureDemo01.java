package com.shf.ssyx;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo01 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("main begin---");

//        runAsync方法不支持返回值
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int result = 1024;
            System.out.println("result:" + result);
        }, executorService);

        System.out.println("main over ----");
    }
}
