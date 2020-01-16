package com.tqazy.qiniu;

public class Test {

    public static void main(String[] args) {
        QiNiuService qiniu = new QiNiuServiceImpl();
        String url = qiniu.initParam("http://qiniu.tqazy.com","/test/img/haitan.jpg","2100-01-01 00:00:00", "KEY1");
        System.out.println(url);
    }
}
