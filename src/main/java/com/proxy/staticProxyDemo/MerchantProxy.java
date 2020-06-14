package com.proxy.staticProxyDemo;

public class MerchantProxy implements Shopping{

    private Merchant merchant;
    @Override
    public void shop(String goodsName) {
        before();
        merchant.shop(goodsName);
        after();
    }

    public MerchantProxy(Merchant merchant) {
        this.merchant = merchant;
    }

    private void before(){
        System.out.println("选择商品");
    }
    private void after(){
        System.out.println("打印发票");
    }
}
