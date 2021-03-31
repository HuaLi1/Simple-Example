package com.daoService;

import com.alibaba.fastjson.JSON;
import com.dao.CityMapper;
import com.entity.CityEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class CityMybatisService {
    /*日志*/
    private static final Log LOG = LogFactory.getLog(CityMybatisService.class);

    @Autowired
    private CityMapper cityMapper;
    public int insertCity(CityEntity cityEntity) {
        LOG.info("要添加的实体："+ JSON.toJSONString(cityEntity));
        if (null == cityEntity){
            return 0;
        }
        int count = cityMapper.insertCity(cityEntity);
        LOG.info("使用mybatis添加city成功");
        return count;

    }
    public List<CityEntity> selectCity(CityEntity cityEntity) {
        LOG.info("查询条件："+ JSON.toJSONString(cityEntity));
        if (null == cityEntity){
            return null;
        }
        List<CityEntity> cityEntities = cityMapper.selectCity(cityEntity);
        if (CollectionUtils.isEmpty(cityEntities)){
            return null;
        }
        LOG.info("查询结果："+JSON.toJSONString(cityEntities));
        return cityEntities;

    }


}
