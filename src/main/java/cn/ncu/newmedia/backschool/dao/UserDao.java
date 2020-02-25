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

    List<User> listAll(@Param("begin")int begin,
                       @Param("num")int num);

    int delete(@Param("userId") int userId);

    int deleteManager(int userId);

    int insert(@Param("user") User user);

    Integer getAllCnt();

    User getGroupManagerByLoc(@Param("loc") String loc);
}
