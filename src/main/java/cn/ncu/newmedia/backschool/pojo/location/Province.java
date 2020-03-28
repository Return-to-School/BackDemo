package cn.ncu.newmedia.backschool.pojo.location;

import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:52
 * @description
 */
@Alias("Province")
public class Province {
    private int provinceId;

    private String name;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
