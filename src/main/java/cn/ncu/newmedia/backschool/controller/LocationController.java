package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.pojo.location.City;
import cn.ncu.newmedia.backschool.pojo.location.Province;
import cn.ncu.newmedia.backschool.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/15 20:33
 * @description
 */
@Controller
@RequestMapping("/location")
public class LocationController {


    @Autowired
    private LocationService locationService;

    /**
     * 获取全国的所哟省份
     * @return
     */
    @RequestMapping(value = "/all-provinces",method = RequestMethod.GET)
    @ResponseBody
    public List<Province> listAllProvince(){
        return locationService.listAllProvinces();
    }

    /**
     * 通过省份的id获取该省份下的所有城市
     * @param provinceId
     * @return
     */
    @RequestMapping(value = "/cities/{provinceId}",method = RequestMethod.GET)
    @ResponseBody
    public List<City> getCitiesByProvinceId(@PathVariable("provinceId") String provinceId){
        return locationService.getCitiesByProvinceId(provinceId);
    }


    /**
     * 通过城市的id获取该城市下的所有的县
     * @param cityId
     * @return
     */
    @RequestMapping(value = "/counties/{cityId}",method = RequestMethod.GET)
    @ResponseBody
    public List<City> getCountiesByCityId(@PathVariable("cityId") String cityId){
        return locationService.getCountiesByCityId(cityId);
    }

}
