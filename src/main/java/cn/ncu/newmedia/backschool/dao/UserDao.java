package cn.ncu.newmedia.backschool.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    String getPasswordByAccount(@Param("account")String account);

    int userExist(@Param("account")String account);


}
