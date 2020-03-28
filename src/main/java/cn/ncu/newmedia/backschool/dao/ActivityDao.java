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



    /*通用获取单个实体的方法*/
    Activity getActivityByColumn(@Param("column") String column, @Param("value") String value);


    /*获取list*/
    List<Activity> listAll();

    List<Activity> filterActivityByColumn(@Param("column")String column,@Param("key") String key);

    List<Activity> getActivitiesByGroupManagerId(@Param("userId") int userId);

    /**
     * 获取所有正在进行的活动
     * @return
     */
    List<Activity> listAllUnderwayAct();


    /*获取宣传组管理员下所有正在进行的活动
    * */
    List<Activity> listGroupUnderwayAct(@Param("managerId") int managerId);

    /*获取所有的历史活动
    * */
    List<Activity> listAllHistoryAct();


    /*获取宣传组管理员的所有正在进行的活动
    * */
    List<Activity> listGroupHistoryAct(@Param("managerId")String managerId);


    /*全局模糊查询*/
    List<Activity> search(@Param("key") String key);
}

