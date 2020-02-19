package com.service;

import com.daoService.CityJdbcTemplateDao;
import com.daoService.CityMybatisService;
import entity.CityEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("cityService")
public class CityService {

    private static final Log LOG = LogFactory.getLog(CityJdbcTemplateDao.class);

    @Resource
    private CityJdbcTemplateDao cityJdbcTemplateDao;

    @Resource
    private CityMybatisService cityMybatisService;

    public void insertCityByJdbcTemplate(){
        CityEntity city = new CityEntity();
        city.setName("chongqing");
        city.setCountryCode("CN");
        city.setDistrict("chongqing");
        city.setPopulation(280000000);
        cityJdbcTemplateDao.insertCity(city);
    }

    public void insertCityByMybatis(){
        CityEntity city = new CityEntity();
        city.setId(1);
        city.setName("shanghai");
        city.setCountryCode("CN");
        city.setDistrict("shanghai");
        city.setPopulation(3000000);
        city.setCreateTime(new Date());
        cityMybatisService.insertCity(city);
    }

    public List<CityEntity> selectCityByMybatis(String name){
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(name);
        List<CityEntity> entities = cityMybatisService.selectCity(cityEntity);
        return entities;
    }

}
