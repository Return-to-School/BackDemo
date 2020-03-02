package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/17 20:55
 * @description
 */
@Repository
@Mapper
public interface FeedBackDao {

    int save(@Param("feedback") Feedback feedBack);

    Feedback getFeedbackByColumn(@Param("column") String column, @Param("value") Object value);


    int delete(@Param("feedbackId") Integer feedbackId);

    Feedback getFeedbackById(@Param("feedbackId")Integer feedbackId);

    int deleteByApplyId(@Param("applyId") int applyId);

    int update(Feedback feedback);
}
