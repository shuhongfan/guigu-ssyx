package com.shf.ssyx;

import io.swagger.models.auth.In;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo04 {
    public static void main(String[] args) {
//        创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("main begin...");

//        1. 线程1 返回结果1024
        CompletableFuture<Integer> futureA = CompletableFuture.supplyAsync(() -> {
            int value = 1024;
            System.out.println("任务1:" + value);
            return value;
        }, executorService);

//        2. 任务2 获取任务1 返回结果
//        thenApply 方法：当一个线程依赖另一个线程时，获取上一个任务返回的结果，并返回当前任务的返回值。
//        thenAccept方法：消费处理结果。接收任务的处理结果，并消费处理，无返回结果。
//        带有Async默认是异步执行的。这里所谓的异步指的是不在当前线程内执行。
        CompletableFuture<Integer> futureB = futureA.thenApplyAsync(res -> {
            System.out.println("任务2：" + res);
            return res;
        }, executorService);

//        3. 任务3 往下执行
//        thenRun方法：只要上面的任务执行完成，就开始执行thenRun，只是处理完任务后，执行 thenRun的后续操作
        CompletableFuture<Void> futureC = futureA.thenRunAsync(() -> {
            System.out.println("任务3：");
        }, executorService);

        System.out.println("main over...");
    }
}
