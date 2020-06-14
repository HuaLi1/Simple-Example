package com.proxy.staticProxyDemo;

public class Consumer {

    public static void main(String[] args){
        Merchant merchant = new Merchant("张三");
        /*代理*/
        MerchantProxy proxy = new MerchantProxy(merchant);
        proxy.shop("iPhone");

    }
}
