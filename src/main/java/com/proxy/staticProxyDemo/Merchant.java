package com.proxy.staticProxyDemo;

import com.entity.CityEntity;

import java.util.ArrayList;
import java.util.List;

public class Merchant implements Shopping {

    private String name;
    public Merchant(String name){
        this.name=name;
    }

    @Override
    public void shop(String goodsName) {
        List<CityEntity> cityEntities = new ArrayList<>();
        cityEntities.add(new CityEntity("广州","45796"));
        cityEntities.add(new CityEntity("北京","00012"));
        cityEntities.add(new CityEntity("杭州","00125"));
        cityEntities.forEach(CityEntity -> System.out.println(CityEntity.getName()));
        System.out.println(name+"购买了商品："+goodsName);
    }
}
