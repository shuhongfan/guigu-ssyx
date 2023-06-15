package com.shf.ssyx;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("main begin---");

//        supplyAsync可以支持返回值
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int value = 1024;
            System.out.println("value:" + value);
            return value;
        }, executorService);

        Integer value = completableFuture.get();

        System.out.println("main over ----"+value);
    }
}
