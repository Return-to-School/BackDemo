package cn.ncu.newmedia.backschool.pojo;

import cn.ncu.newmedia.backschool.Enumeration.RoleEnum;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/12 16:23
 * @description
 */
@Alias("User")
public class User {

    private int id;

    private String account;

    private String password;

    private RoleEnum role;

    private String loc;

    private List<Activity> activities;
    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }


    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", role = "+ role.getDesc()+
                ", loc = "+ loc+
                '}';
    }
}
