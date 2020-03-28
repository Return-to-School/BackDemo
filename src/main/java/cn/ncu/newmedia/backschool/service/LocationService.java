package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.LocationDao;
import cn.ncu.newmedia.backschool.pojo.location.City;
import cn.ncu.newmedia.backschool.pojo.location.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/15 20:35
 * @description
 */
@Service
public class LocationService {

    @Autowired
    private LocationDao locationDao;
    public List<Province> listAllProvinces() {
        return locationDao.listAllProvinces();
    }

    public List<City> getCitiesByProvinceId(String provinceId) {
        return locationDao.getCitiesByProvinceId(provinceId);
    }

    public List<City> listAllColleges() {
        return locationDao.listAllColleges();
    }

    public List<City> getCountiesByCityId(String cityId) {
        return locationDao.getCountiesByCityId(cityId);
    }

    public List<City> getClassesByCollegeId(int collegeId) {
        return locationDao.getClassesByCollegeId(collegeId);
    }
}
