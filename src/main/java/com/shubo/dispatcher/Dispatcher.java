package com.shubo.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by horseman on 2016/12/16.
 */
public class Dispatcher {
    public static void setThreadCnt(int cnt) {
        currentThreadCnt = cnt;
    }
    private static int currentThreadCnt  = 10;
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    protected static int getActiveCount() {
        return ((ThreadPoolExecutor) fixedThreadPool).getActiveCount();
    }

    /*
     * 同步方法，直到有线程执行才返回
     */
    public static void startTaskSync(Runnable runnable) {
        boolean isRunning = false;
        while (!isRunning) {
            if (getActiveCount() < currentThreadCnt) {
                fixedThreadPool.execute(runnable);
                isRunning = true;
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
