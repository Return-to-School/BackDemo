package cn.ncu.newmedia.backschool.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author maoalong
 * @date 2020/1/19 19:41
 * @description
 */
@Repository
@Mapper
public interface ActivityManagerDao {

    int add(@Param("userId") int userId,@Param("activityId") int activityId);

    int isManagedByGroup(@Param("activityId") Integer activityId,@Param("userId") String userId);

    /*管理员相关的方法*/
    int deleteManagerByActId(@Param("activityId")Integer activityId);

}
