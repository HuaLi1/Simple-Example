package com.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

//实现对线程同步下载图片
public class IODemo1 extends Thread{
    private String url;
    private String fileName;
    public IODemo1(String url,String fileName){
        this.url =url;
        this.fileName=fileName;
    }
    public void run(){
        WebDownLoader webDownLoader = new WebDownLoader();
        try {
            webDownLoader.downLoader(url,fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("下载了文件："+fileName);
    }

    public static void main(String[] arg){
        IODemo1 ioDemo1 = new IODemo1("https://i0.hdslb.com/bfs/sycp/creative_img/202003/4edaa3c0623d70984639f507ce042c44.jpg@1375w_605h_1c_100q.webp","1.jpg");
        IODemo1 ioDemo2 = new IODemo1("https://i0.hdslb.com/bfs/archive/f8be29aedc88d1feb1209947582f7035e21150a3.jpg@412w_232h_1c_100q.jpg","2.jpg");
        IODemo1 ioDemo3 = new IODemo1("https://i0.hdslb.com/bfs/archive/d626f8f7fa29330215d20328da9100a1df7cf45f.jpg@412w_232h_1c_100q.jpg","3.jpg");
        ioDemo1.start();
        ioDemo2.start();
        ioDemo3.start();


    }

}
class WebDownLoader{
    //下载方法
    public void downLoader(String url,String fileName) throws MalformedURLException {
        try {
            FileUtils.copyURLToFile(new URL(url),new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，WebDownLoader，下载文件异常");
        }
    }
}
