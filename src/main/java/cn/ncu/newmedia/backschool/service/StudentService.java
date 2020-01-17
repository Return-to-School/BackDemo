package cn.ncu.newmedia.backschool.service;

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

    @Transactional
    public boolean saveStudent(Student student) {
        return studentDao.insert(student)>0;
    }

    @Transactional
    public boolean updateStudent(Student student) {
        return studentDao.update(student)>0;
    }

    public boolean idCardHasMatch(String idCard, String name) {
        return studentDao.idCardHashMatch(idCard,name)>0;
    }

    public Student getStudentByColumn(String column,Object value){
        return studentDao.getStudentByColumn(column,value);
    }

}
