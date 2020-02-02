package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/13 20:49
 * @description
 */
@Repository
@Mapper
public interface ActivityDao{

    int insert(@Param("activity") Activity activity);

    int update(@Param("activity") Activity activity);

    Activity getActivityByColumn(@Param("column") String column, @Param("value") String value);

    Activity getActivityById(@Param("activityId") Integer activityId);

    List<Activity> listAll(@Param("begin")Integer begin,@Param("num")Integer num);

    int getTotalCnt();

    int delete(@Param("activityId") Integer activityId);

    int deleteManagerByActId(@Param("activityId")Integer activityId);

    List<Activity> filterActivityByColumn(@Param("column")String column,@Param("key") String key);

    List<Activity> getActivityByGroupManagerId(@Param("userId") int userId);

    List<Activity> listAllUnderwayAct(@Param("begin")Integer begin,@Param("num")Integer num);

    int getUnderwayTolAct();

    List<Activity> listGroupUnderwayAct(@Param("begin")Integer begin,@Param("num")Integer num,@Param("managerId") int managerId);

    int getGroupUnderwayTolAct();

    int isManagedByGroup(@Param("activityId") Integer activityId,@Param("userId") String userId);

}

