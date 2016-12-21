package com.shubo;

import com.shubo.dispatcher.Dispatcher;
import com.shubo.stastics.AnalyticalResult;
import com.shubo.parser.PDF2TXT;
import com.shubo.sniff.TableSniffer;
import com.shubo.util.HorsemanUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import static com.shubo.parser.PDF2TXT.*;

/**
 * Created by horseman on 2016/11/28.
 */
public class ReportParser {

    public static CountDownLatch latch = new CountDownLatch(1);

    public static void test() {
        handle(new File("E:/年报解析/html/000415/渤海租赁2013年年度报告.html"), "000415");
    }
    public static void main(String args[]) {
        System.out.println("------开始处理-------");

        // 单个文件调试专用
        if (args.length == 1 && args[0].equals("test")) {
            test();
            return;
        }
        if (args.length > 1) {
            Dispatcher.setThreadCnt(Integer.valueOf(args[0]));
            AppContext.setRootFolder(args[1]);
        }
        try {
            /* 年报解析/年报 */
            File folder = new File(AppContext.srcFolder);

            for (File subFolder : folder.listFiles()) {

                /* subFolder : 000001 002625 */
                if (subFolder.isDirectory()) {
                    for (File file : subFolder.listFiles()) {
                        if (!file.getName().contains("英文版")
                                && !file.getName().contains("摘要")
                                && file.getName().contains("年度报告")
                                && !file.getName().contains("半年度报告")) {

                            try {
                                Dispatcher.startTaskSync(() -> {
                                    handle(file, subFolder.getName());
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("任务派发完毕，等待任务执行");

            try {
                latch.await();
                System.out.println("任务执行完毕,准备写入结果文件");

                AnalyticalResult.writeTotalToCsv();

                System.out.println("写入结果文件结束,程序退出");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Dispatcher.shutdown();
        }
    }

    public static void handle(File file, String stockCode) {
        try {
            String needHandleFileName = stockCode + "-" + file.getName();

            AnalyticalResult.createResult(needHandleFileName);

            System.out.println("开始识别:" + needHandleFileName);
            String needHandleFilePath = AppContext.yearReportFolder + File.separator + needHandleFileName;

            FileUtils.copyFile(file, new File(needHandleFilePath));

            List<String> list = PDF2TXT.parsePDFStructure(needHandleFilePath);

                                    /* 存储除了八大表以外的其他表字符串 */
            List<String> otherEntityString = new ArrayList<>();

            // 记录已经获取过实体的类，防止重复获取
            List<String> capturedEntityKeys = new ArrayList<>();

            HorsemanUtils.createKeys(needHandleFileName);
            //keyQueue.
            for (String str : list) {
                if (str.startsWith(typeTitle + splitChar)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeText + splitChar)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeTitle)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeText)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeTable + splitChar)) {
                    if (!TableSniffer.sniffEntity(str, HorsemanUtils.getPossibleKeys(needHandleFileName), needHandleFileName, capturedEntityKeys)) {
                        otherEntityString.add(str);
                    }
                } else {
                    /* what's this */
                }
            }

            // 其他
            TableSniffer.sniffEachEntity(otherEntityString, needHandleFileName);

            AnalyticalResult.writeToCsv(needHandleFileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(file.getName() + " 执行完毕");
            Dispatcher.decrease();
        }
    }
}
