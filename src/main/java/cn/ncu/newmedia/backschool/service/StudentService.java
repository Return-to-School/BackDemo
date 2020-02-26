package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.dao.StudentDao;
import cn.ncu.newmedia.backschool.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author maoalong
 * @date 2020/1/12 22:30
 * @description
 */
@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    /**
     * 添加一条学生的信息
     * @param student
     * @return
     */
    @Transactional
    public boolean saveStudent(Student student) {
        return studentDao.insert(student)>0;
    }



    /**
     * 更新学生的信息
     * @param student
     * @return
     */
    @Transactional
    public boolean updateStudent(Student student) {
        return studentDao.update(student)>0;
    }

    /**
     * 从基础数据库中验证身份证是否与学生的姓名匹配
     * @param studentCard
     * @param name
     * @return
     */
    public boolean verifyNameAndCard(String studentCard, String name) {
        return studentDao.studentCardHasMatchName(studentCard,name)>0;
    }

    /**
     *
     * @param column
     * @param value
     * @return
     */
    public Student getStudentByColumn(String column,Object value){
        return studentDao.getStudentByColumn(column,value);
    }

    /**
     * 分页获取参与活动的所有学生信息
     * @param activityId
     * @return
     */
    public Page getStudentListInAct(int activityId,int currPage,int pageSize) {
        return PageService.getPage(currPage,pageSize,studentDao,
                e->e.getStudentListInAct(activityId,(currPage-1)*pageSize,pageSize),
                e->e.getCntInAct(activityId));
    }

    /**
     * 分页获取所有的学生信息
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAll(int currPage, int pageSize) {
        return PageService.getPage(currPage,pageSize,studentDao,
                e->e.listAll((currPage-1)*pageSize,pageSize),
                e->e.getAllCnt());
    }

}
