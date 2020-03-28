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

    /**
     * 保存反馈信息
     * @param feedBack
     * @return
     */
    int save(@Param("feedback") Feedback feedBack);

    /**
     * 获取单个反馈的复用方法·
     * @param column
     * @param value
     * @return
     */
    Feedback getFeedbackByColumn(@Param("column") String column, @Param("value") Object value);


    /**
     * 删除指定的反馈
     * @param feedbackId
     * @return
     */
    int delete(@Param("feedbackId") Integer feedbackId);

    /**
     * 获取指定的反馈
     * @param feedbackId
     * @return
     */
    Feedback getFeedbackById(@Param("feedbackId")Integer feedbackId);


    /**
     * 删除申请的反馈
     * @param applyId
     * @return
     */
    int deleteByApplyId(@Param("applyId") int applyId);

    /**
     * 更新反馈
     * @param feedback
     * @return
     */
    int update(Feedback feedback);
}
