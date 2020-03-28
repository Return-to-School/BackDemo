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

    /**
     * 添加一条管理映射关系
     * @param userId
     * @param activityId
     * @return
     */
    int add(@Param("userId") String userId,@Param("activityId") int activityId);


    /**
     * 判断这个活动是不是被管理员管理的
     * @param activityId
     * @param userId
     * @return
     */
    int isManagedByGroup(@Param("activityId") Integer activityId,@Param("userId") String userId);


    /**
     * 删除映射关系
     * @param activityId
     * @return
     */
    int deleteManagerByActId(@Param("activityId")Integer activityId);

}
