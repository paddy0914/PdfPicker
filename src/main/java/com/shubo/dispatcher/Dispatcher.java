package com.shubo.dispatcher;

import com.shubo.ReportParser;

import java.util.concurrent.*;

/**
 * Created by horseman on 2016/12/16.
 */
public class Dispatcher {
    public static final int DEFAULT_THREAD_CNT = 10;
    public static void setThreadCnt(int cnt) {
        currentThreadCnt = cnt;
    }
    private static int currentThreadCnt  = DEFAULT_THREAD_CNT;
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(currentThreadCnt);

    protected static int getActiveCount() {
        return ((ThreadPoolExecutor) fixedThreadPool).getActiveCount();
    }

    public static void shutdown() {
        fixedThreadPool.shutdown();
    }

    /*
     * 同步方法，直到有线程执行才返回
     */
    public static void startTaskSync(Runnable runnable) {
        boolean isRunning = false;
        while (!isRunning) {
            if (get() < currentThreadCnt) {
                fixedThreadPool.execute(runnable);
                increase();
                isRunning = true;
            } else {
                delay(200);
            }
        }

        startWatch();
    }

    public static Object cntLock = new Object();
    public static int currentHandingCnt = 0;

    public static void increase() {
        synchronized (cntLock) {
            currentHandingCnt ++;
//            System.out.println(" increase current " + currentHandingCnt);
        }
    }

    public static void decrease() {
        synchronized (cntLock) {
            currentHandingCnt --;
//            System.out.println(" decrease current " + currentHandingCnt);
        }
    }

    public static int get() {
        return currentHandingCnt;
    }

    public static boolean isStart = false;
    public static void startWatch() {
        new Thread(() -> {
            if (isStart) {
                return;
            } else {
                isStart = true;
            }
            do {
                delay(2000);
            } while (currentHandingCnt > 0);

            ReportParser.latch.countDown();
        }).start();
    }

    private static void delay(int mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
