package cn.ncu.newmedia.backschool.pojo.location;

import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:52
 * @description
 */
@Alias("County")
public class County {
    private int countyId;

    private String name;

    private int city;

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }
}
