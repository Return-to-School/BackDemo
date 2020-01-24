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

    User getUserById(@Param("userId") Integer userId);

    User getUserByAccount(@Param("account")String account);

    List<User> getGroupManagerById(@Param("userId")int userId);

    List<User> listAll();

    int delete(@Param("userId") int userId);

    int deleteManager(int userId);

    int insert(@Param("user") User user);
}
