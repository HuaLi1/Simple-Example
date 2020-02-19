package com.dao;

import entity.CityEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityMapper {

    int insertCity(CityEntity cityEntity);

    List<CityEntity> selectCity(CityEntity cityEntity);
}
