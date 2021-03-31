package com.thread;

import com.service.CityService;
import com.service.SingletonCityService;
import com.service.SpringContextHolder;
import com.entity.CityEntity;

import javax.annotation.Resource;
import java.util.Date;

public class ThreadDemo3 extends Thread{

    private SpringContextHolder springContextHolder;

    @Resource
    private CityService cityService;
    public ThreadDemo3(SpringContextHolder springContextHolder) {
        super();
        /* 在线程对象中无法通过spring注入service， 需要通过springContextHolder加载获取  */
        this.springContextHolder = springContextHolder;
        this.cityService= (CityService ) springContextHolder.getBean("cityService");
    }

    @Override
    public void run() {
        SingletonCityService singletonCityService = SingletonCityService.getInstance();

        CityEntity cityEntity = new CityEntity("01天","123403");
        cityEntity.setId(10125);
        cityEntity.setCreateTime(new Date());
        for (int i=0;i<1000;i++){

        }
        cityService.insert(cityEntity);
        /*List<CityEntity> cityEntities = cityService.selectCity(cityEntity);
        if (cityEntities == null){
            System.out.println("插入城市信息："+ JSON.toJSONString(cityEntity));

        }else {
            System.out.println("该城市已经存在了");
        }*/

    }

}
