package com.dao;

import com.entity.CityEntity;

import java.util.List;

public interface ICityDao {
    int insertCity(CityEntity city);
    int insertCityByList(List<CityEntity> cityEntityList);
    List<CityEntity> selectCity(CityEntity cityEntity);
}
