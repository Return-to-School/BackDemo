package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    String getPasswordByAccount(@Param("account")String account);

    int userExist(@Param("account")String account);

    List<User> getUserByColumn(@Param("column") String column, @Param("value") Object value);

    List<User> getGroupManagerById(@Param("userId")int userId);

    List<User> listAll();

    int delete(@Param("userId") int userId);

    int deleteManager(int userId);
}
