package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {


    User getUserById(@Param("userId") String userId);

    List<User> listAll();

    int delete(@Param("userId") String userId);

    int deleteManager(String userId);

    int insert(@Param("user") User user);

    /**
     * 获取某个地区的管理员账号信息
     * @param loc
     * @return
     */
    User getGroupManagerByLoc(@Param("loc") String loc);

    int update(User user);
}
