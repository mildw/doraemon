package com.example.async.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class AsyncTestService {

    private final RequestService requestService;

    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletableFuture<String>[] futures = new CompletableFuture[3];
        for(int i=0; i<3; i++) {
            futures[i] = CompletableFuture.supplyAsync(() -> {
                System.out.println("call");
                return requestService.request();
            }, executor);
        }
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futures);
        allOfFuture.join();
        System.out.println("done");
    }

    public void runtimeException() {
        ExecutorService executor = Executors.newCachedThreadPool();

        CompletableFuture<Boolean>[] futures = new CompletableFuture[3];
        for(int i=0; i<3; i++) {
            futures[i] = CompletableFuture.supplyAsync(() -> {
                System.out.println("call");
                return requestService.runtimeException();
            }, executor).exceptionally(e -> {
                System.out.println(e.getMessage());
                return false;
            });
        }

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futures);
        allOfFuture.join();
        boolean result = true;
        for (CompletableFuture<Boolean> future : futures) {
            System.out.println("result : "+future.join());
            result &= future.join();
        }
        System.out.println("final : " + result);
    }
}
