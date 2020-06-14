package com.service;

import com.daoService.CityMybatisService;
import entity.CityEntity;

import javax.annotation.Resource;
import java.util.List;

public class SingletonCityService {
    private volatile static SingletonCityService instance = null;

    @Resource
    private CityMybatisService cityMybatisService;
    private SingletonCityService() {
    }

    public static SingletonCityService getInstance() {
        //第一重判断
        if (instance == null) {
            //锁定代码块
            synchronized (SingletonCityService.class) {
                //第二重判断
                if (instance == null) {
                    instance = new SingletonCityService(); //创建单例实例
                }
            }
        }
        return instance;
    }

    public int insert(CityEntity cityEntity){
        if (cityMybatisService == null){
            cityMybatisService = new CityMybatisService();
        }
        if (null != cityEntity){
            return cityMybatisService.insertCity(cityEntity);
        }else {
            return 0;
        }
    }
    public List<CityEntity> selectCity(CityEntity cityEntity){
        if (cityMybatisService == null){
            cityMybatisService = new CityMybatisService();
        }
        List<CityEntity> entities = cityMybatisService.selectCity(cityEntity);
        return entities;
    }
}
