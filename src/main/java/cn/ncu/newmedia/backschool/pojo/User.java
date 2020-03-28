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

    private String userId;

    private String password;

    private RoleEnum role;

    private String loc;



    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", role = "+ role.getDesc()+
                ", loc = "+ loc+
                '}';
    }
}
