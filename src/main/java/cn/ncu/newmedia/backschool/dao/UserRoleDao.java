package cn.ncu.newmedia.backschool.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface UserRoleDao {
    Set<String> getRolsByAccount(@Param("account")String account);

}
