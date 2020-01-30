package cn.ncu.newmedia.backschool.dao;

import cn.ncu.newmedia.backschool.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
@Mapper
public interface StudentDao {
    int insert(@Param("student") Student student);

    int update(@Param("student") Student student);

    int idCardHashMatch(@Param("idCard") String idCard,@Param("name") String name);

    Student getStudentByColumn(@Param("column") String column, @Param("value") Object value);

    int  delete(@Param("studentId") int studentId);

    List<Student> getStudentListInAct(@Param("activityId") int activityId);

    List<Student> listAll();

    List<Student> searchByColumn(@Param("column") String column,@Param("key")String key);
}
