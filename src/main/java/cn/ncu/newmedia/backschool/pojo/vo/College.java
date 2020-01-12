package cn.ncu.newmedia.backschool.pojo.vo;

import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:51
 * @description
 */
@Alias("College")
public class College {
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
