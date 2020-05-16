package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.vo.pc.ApplyVoPC;
import cn.ncu.newmedia.backschool.pojo.vo.pc.Keys;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ApplyDao {

    /*增删查*/
    int insert(@Param("apply") Apply apply);
    int delete(@Param("applyId") int id);
    int deleteByActId(@Param("activityId")Integer activityId);
    Apply getApplyById(@Param("applyId") int applyId);

    /*获取所有的记录*/
    List<Apply> listAll();


    /*list复用方法*/
    List<Apply> getAppliesByColumn(@Param("column")String column,@Param("value") Object value);

    /*修改申请状态*/
    int changeApplyStatus(@Param("applyId")int applyId,@Param("status") ApplyStatus status);


    /*获取一个活动下的所有申请，包含学生信息*/
    List<ApplyVoPC> getApplyVoPcsByActId(@Param("activityId") Integer activityId);

    /*根据字段搜索申请*/
    List<ApplyVoPC> getAppVoListByKeys(@Param("keys")Keys keys);


    /*获取某个活动下申请通过的学生申请*/
    List<ApplyVoPC> getPassStudentApply(@Param("activityId") int activityId);


    /*通过活动id和学生id查找申请*/
    Apply getApplyByAidAndSid(@Param("activityId") int activityId,
                              @Param("studentId")String studentId);


}
