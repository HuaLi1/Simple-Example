package com.daoService;

import com.alibaba.fastjson.JSON;
import com.dao.ICityDao;
import entity.CityEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CityJdbcTemplateDao implements ICityDao {

    /*日志*/
    private static final Log LOG = LogFactory.getLog(CityJdbcTemplateDao.class);

    public CityJdbcTemplateDao(){
        LOG.info("通过JdbcTemplate来访问数据库");
    }

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public int insertCity(CityEntity city) {
        if (null == city){
            return 0;
        }
        LOG.info("添加城市start："+ JSON.toJSONString(city));
        String sql = "INSERT INTO `yao`.`city` (`ID`,`Name`, `CountryCode`, `District`, `Population`,`CREATE_TIME`) " +
                "VALUES (:ID,:name,:countryCode,:district,:population,:createTime)";
        Map<String,Object> addMap = new HashMap<>();
        addMap.put("ID",1);
        addMap.put("name",city.getName());
        addMap.put("countryCode",city.getCountryCode());
        addMap.put("district",city.getDistrict());
        addMap.put("population",city.getPopulation());
        addMap.put("createTime",new Date());
        int count = jdbcTemplate.update(sql,addMap);
        LOG.info("添加城市end："+ JSON.toJSONString(city));
        return count;
    }

    @Override
    public int insertCityByList(List<CityEntity> cityEntityList) {
        return 0;
    }

    @Override
    public List<CityEntity> selectCity(CityEntity cityEntity) {
        return null;
    }
}
