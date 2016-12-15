package com.shubo;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Created by liujinping on 2016/12/15.
 */
public class FileExtraction {


    public static void main(String[] args) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //创建远程文件对象
            String remoteUrl = "smb://192.168.0.109/myshare/pdf_2_html_results/hexun/";
            SmbFile remoteFile = new SmbFile(remoteUrl);
            remoteFile.connect(); //尝试连接
            //创建文件流
            for (SmbFile subsmbFile : remoteFile.listFiles()) {
                try {
                    if (subsmbFile.isDirectory()) {

                        String subsmbUrl = remoteUrl + subsmbFile.getName();

                        String localUrl = "D:/年报解析/年报Test" + "/" + subsmbFile.getName();

                        File f = new File(localUrl);
                        if (!f.exists()) {
                            f.mkdir();
                        }

                        SmbFile yrFolder = new SmbFile(subsmbUrl + "年报" + "/");
                        if (yrFolder.exists() && yrFolder.isDirectory()) {
                            for (SmbFile smbfile : yrFolder.listFiles()) {
                                if (smbfile.getName().endsWith("html")) {
                                    //File localFile=new File();
                                    System.out.println("start: "+yrFolder.getName().replace("//","")+"-"+smbfile.getName());
                                    String localFileUrl = localUrl + "年报" + "/" + smbfile.getName();
                                    File rfile=new File(localUrl+"年报");
                                    if(!rfile.exists()){
                                        rfile.mkdir();
                                    }

                                    File file = new File(localFileUrl);
                                    if(!file.exists()){
                                        file.createNewFile();
                                    }
                                    in = new BufferedInputStream(new SmbFileInputStream(smbfile));
                                    out = new BufferedOutputStream(new FileOutputStream(file));

                                    byte[] buffer = new byte[4096];
                                    int len = 0; //读取长度
                                    while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                                        out.write(buffer, 0, len);
                                    }
                                    out.flush(); //刷新缓冲的输出流

                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    String msg = "下载远程文件出错：" + e.getLocalizedMessage();
                    System.out.println(msg);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
