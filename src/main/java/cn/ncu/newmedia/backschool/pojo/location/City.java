package cn.ncu.newmedia.backschool.pojo.location;

import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:53
 * @description
 */
@Alias("City")
public class City {
    private int cityId;

    private String name;

    private int province;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int id) {
        this.cityId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }
}
