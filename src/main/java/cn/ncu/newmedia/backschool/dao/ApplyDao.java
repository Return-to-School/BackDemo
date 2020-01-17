package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Apply;
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
}
