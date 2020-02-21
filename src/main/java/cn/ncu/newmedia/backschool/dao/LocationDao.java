package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.vo.City;
import cn.ncu.newmedia.backschool.pojo.vo.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/15 20:35
 * @description
 */
@Repository
@Mapper
public interface LocationDao {

    List<Province> listAllProvinces();

    List<City> getCitiesByProvinceId(@Param("provinceId") int provinceId);

    List<City> listAllColleges();

    List<City> getCountiesByCityId(@Param("cityId") int cityId);

    List<City> getClassesByCollegeId(@Param("collegeId") int collegeId);
}
