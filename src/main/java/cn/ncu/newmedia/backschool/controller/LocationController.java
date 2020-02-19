package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.pojo.vo.City;
import cn.ncu.newmedia.backschool.pojo.vo.Province;
import cn.ncu.newmedia.backschool.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/all-provinces",method = RequestMethod.GET)
    @ResponseBody
    public List<Province> listAllProvince(){
        return locationService.listAllProvinces();
    }

    @RequestMapping(value = "/cities/{provinceId}",method = RequestMethod.GET)
    @ResponseBody
    public List<City> getCitiesByProvinceId(@PathVariable("provinceId") int provinceId){
        return locationService.getCitiesByProvinceId(provinceId);
    }

    @RequestMapping(value = "/counties/{cityId}",method = RequestMethod.GET)
    @ResponseBody
    public List<City> getCountiesByCityId(@PathVariable("cityId") int cityId){
        return locationService.getCountiesByCityId(cityId);
    }

}
