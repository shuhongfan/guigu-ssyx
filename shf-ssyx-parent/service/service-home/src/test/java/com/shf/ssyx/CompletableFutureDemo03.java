package com.shf.ssyx;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo03 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("main begin...");

//        whenComplete：是执行当前任务的线程执行继续执行 whenComplete 的任务。
//        whenCompleteAsync：是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行。
//        方法不以Async结尾，意味着Action使用相同的线程执行，而Async可能会使用其他线程执行（如果是使用相同的线程池，也可能会被同一个线程选中执行）
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int value = 1024;
            System.out.println("value:" + value);
            return value;
        }, executorService).whenComplete((rs,e)->{
            System.out.println("whenComplete:"+rs);
            System.out.println("exception:" + e);
        });

        System.out.println("main over...");
    }
}
