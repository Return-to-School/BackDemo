package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.location.City;
import cn.ncu.newmedia.backschool.pojo.location.Province;
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

    /**
     * 获取所有的省份信息
     * @return
     */
    List<Province> listAllProvinces();


    /**
     * 获取省份下的所有城市信息
     * @param provinceId
     * @return
     */
    List<City> getCitiesByProvinceId(@Param("provinceId") String provinceId);

    /**
     * 获取城市下的所有的县级行政厅信息
     * @param cityId
     * @return
     */
    List<City> getCountiesByCityId(@Param("cityId") String cityId);


    /**
     * 获取所有的学院的信息
     * @return
     */
    List<City> listAllColleges();


    /**
     * 获取学院下的所有的班级信息
     * @param collegeId
     * @return
     */
    List<City> getClassesByCollegeId(@Param("collegeId") int collegeId);
}
