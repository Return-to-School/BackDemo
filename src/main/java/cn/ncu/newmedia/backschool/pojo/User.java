package cn.ncu.newmedia.backschool.pojo;

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

    /*管理的活动*/
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", activities=" + (activities==null?"null":activities.toString())+
                '}';
    }
}
