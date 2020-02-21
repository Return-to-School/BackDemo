package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.LocationDao;
import cn.ncu.newmedia.backschool.pojo.vo.City;
import cn.ncu.newmedia.backschool.pojo.vo.Province;
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

    public List<City> getCitiesByProvinceId(int provinceId) {
        return locationDao.getCitiesByProvinceId(provinceId);
    }

    public List<City> listAllColleges() {
        return locationDao.listAllColleges();
    }

    public List<City> getCountiesByCityId(int cityId) {
        return locationDao.getCountiesByCityId(cityId);
    }

    public List<City> getClassesByCollegeId(int collegeId) {
        return locationDao.getClassesByCollegeId(collegeId);
    }
}
