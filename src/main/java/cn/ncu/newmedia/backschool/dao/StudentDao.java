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

    /*增删改查方法*/
    int insert(@Param("student") Student student);

    int update(@Param("student") Student student);

    int  delete(@Param("studentId") String studentId);

    /**
     * 验证学生姓名与学号是否匹配
     * @param studentId
     * @param name
     * @return
     */
    int studentIdHasMatchName(@Param("studentId") String studentId, @Param("name") String name);


    /*获取学生信息的复用方法*/
    Student getStudentByColumn(@Param("column") String column, @Param("value") Object value);


    /**
     * 获取参与某个活动的所有学生信息
     * @param activityId
     * @return
     */
    List<Student> getStudentListInAct(@Param("activityId") int activityId);


    List<Student> listAll();


}
