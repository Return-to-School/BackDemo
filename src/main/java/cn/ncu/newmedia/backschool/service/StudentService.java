package cn.ncu.newmedia.backschool.service;

import cn.ncu.newmedia.backschool.dao.Page;
import cn.ncu.newmedia.backschool.dao.StudentDao;
import cn.ncu.newmedia.backschool.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/12 22:30
 * @description
 */
@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;


    private final char[] codes = {'1', '0' ,'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    private final int[] weight = {7,9,10, 5 ,8 ,4 ,2 ,1 ,6 ,3 ,7, 9, 10, 5, 8, 4, 2};

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
        return studentDao.studentIdHasMatchName(studentCard,name)>0;
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
    public List<Student> getStudentListInAct(int activityId) {
        return studentDao.getStudentListInAct(activityId);
    }

    /**
     * 分页获取所有的学生信息
     * @param currPage
     * @param pageSize
     * @return
     */
    public Page listAll(int currPage, int pageSize) {
        return PageService.getPage(currPage,pageSize,studentDao,
                e->e.listAll());
    }

    /*身份证校验*/
    public boolean checkIdCard(String idCard) {
        int sum = 0;
        for(int i = 0;i<idCard.length()-1;i++){
            sum+=(idCard.charAt(i)-'0')*weight[i];
        }

        char code = codes[sum%11];
        return code ==idCard.charAt(idCard.length()-1);
    }
}
