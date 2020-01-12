package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StudentDao {
    int addStudent(@Param("student") Student student);

    int updateStudent(@Param("student") Student student);

    int idCardHashMatch(@Param("idCard") String idCard,@Param("name") String name);
}
