package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.vo.ApplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ApplyDao {

    int insert(@Param("apply") Apply apply);

    List<Apply> listAll();

    List<Apply> getAppliesByColumn(@Param("column")String column,@Param("value") Object value);

    int changeApplyStatus(@Param("applyId")int applyId,@Param("status") ApplyStatus status);

    int delete(@Param("applyId") int id);

    int deleteByActId(@Param("activityId")Integer activityId);

    Apply getApplyById(@Param("applyId") int applyId);

    List<ApplyVo> getApplyVoListByColumn(@Param("column")String column,
                                       @Param("value")String value);

}
