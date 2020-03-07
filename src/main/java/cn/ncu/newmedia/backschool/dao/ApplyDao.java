package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import cn.ncu.newmedia.backschool.pojo.vo.Keys;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ApplyDao {

    int insert(@Param("apply") Apply apply);

    List<Apply> listAll(@Param("begin")int begin,@Param("num")int num);

    List<Apply> getAppliesByColumn(@Param("column")String column,@Param("value") Object value);

    int changeApplyStatus(@Param("applyId")int applyId,@Param("status") ApplyStatus status);

    int delete(@Param("applyId") int id);

    int deleteByActId(@Param("activityId")Integer activityId);

    Apply getApplyById(@Param("applyId") int applyId);

    List<ApplyVo> getAppVoForSuper(@Param("keys") Keys keys,
                                 @Param("begin")int begin,
                                 @Param("num")int num);


    Integer getApplyCnt();

    List<Apply> getAppliesByActId(@Param("activityId")int activityId,
                                  @Param("begin")int begin,
                                  @Param("num")int num);

    int getApplyCntInAct(@Param("activityId")int activityId);

    Integer getAppVoForSuperCnt(@Param("keys")Keys keys);


    List<ApplyVo> getAppVoListByKeys(@Param("keys")Keys keys);


    List<ApplyVo> getPassStudentApply(@Param("activityId") int activityId,
                                      @Param("begin") int begin,
                                      @Param("num") int pageSize);

    Integer getPassStudentCnt(@Param("activityId") int activityId);

    Apply getApply(@Param("activityId") int activityId,
                   @Param("studentId")int studentId);
}
