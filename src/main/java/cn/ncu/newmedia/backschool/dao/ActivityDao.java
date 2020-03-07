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

    /*CRUD方法*/
    int insert(@Param("activity") Activity activity);

    int update(@Param("activity") Activity activity);

    Activity getActivityById(@Param("activityId") Integer activityId);

    int delete(@Param("activityId") Integer activityId);


    /*获取数据库记录方法*/
    int getTotalCnt();

    int getGroupUnderwayTolCnt(@Param("userId")int userId);

    int getUnderwayTolCnt();

    int getGroupAllCnt(@Param("userId")int userId);

    int filterActivityByColumnCnt(@Param("column")String column,@Param("key")Object key);

    /*通用获取单个实体的方法*/
    Activity getActivityByColumn(@Param("column") String column, @Param("value") String value);


    /*获取list*/
    List<Activity> listAll(@Param("begin")Integer begin,@Param("num")Integer num);

    List<Activity> filterActivityByColumn(@Param("column")String column,@Param("key") String key,
                                          @Param("begin")int begin,@Param("num")int num);

    List<Activity> getActivitiesByGroupManagerId(@Param("begin") int begin,@Param("num") int num, @Param("userId") int userId);

    List<Activity> listAllUnderwayAct(@Param("begin")Integer begin,@Param("num")Integer num);

    List<Activity> listGroupUnderwayAct(@Param("begin")Integer begin,@Param("num")Integer num,@Param("managerId") int managerId);


    List<Activity> listAllHistoryAct(@Param("begin")Integer begin,@Param("num") Integer num);

    Integer getHistoryActCnt();

    List<Activity> listGroupHistoryAct(@Param("begin")int begin,
                                       @Param("num")int num,
                                       @Param("managerId")int managerId);

    Integer getGroupHistoryActCnt(@Param("managerId") Integer managerId);

}

