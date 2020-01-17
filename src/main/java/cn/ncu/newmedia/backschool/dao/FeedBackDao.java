package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.FeedBack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author maoalong
 * @date 2020/1/17 20:55
 * @description
 */
@Repository
@Mapper
public interface FeedBackDao {

    int save(@Param("feedback")FeedBack feedBack);
}
