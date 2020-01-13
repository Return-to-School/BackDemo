package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author maoalong
 * @date 2020/1/13 20:49
 * @description
 */
@Repository
@Mapper
public interface ActivityDao {

    int insertActivity(@Param("activity") Activity activity);

    int updateActivity(@Param("activity") Activity activity);
}
