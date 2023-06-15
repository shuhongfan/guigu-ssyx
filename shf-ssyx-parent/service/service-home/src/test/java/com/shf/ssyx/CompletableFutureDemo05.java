package com.shf.ssyx;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo05 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

//        任务1 返回1024
        CompletableFuture<Integer> futureA = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": begin..");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int value = 1024;
            System.out.println("任务1：" + value);
            System.out.println(Thread.currentThread().getName() + ":end..");
            return value;
        }, executorService);

//        任务2 返回200
        CompletableFuture<Integer> futureB = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": begin..");
            int value = 200;
            System.out.println("任务2：" + value);
            System.out.println(Thread.currentThread().getName() + ":end..");
            return value;
        }, executorService);

        CompletableFuture<Void> all = CompletableFuture.allOf(futureA, futureB);
        all.join();

        System.out.println("over...");
    }
}
