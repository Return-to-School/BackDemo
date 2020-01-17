package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Apply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ApplyDao {

    int insert(@Param("apply") Apply apply);
}
