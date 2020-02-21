package cn.ncu.newmedia.backschool.pojo.vo;

import cn.ncu.newmedia.backschool.pojo.Activity;
import cn.ncu.newmedia.backschool.pojo.Apply;
import cn.ncu.newmedia.backschool.pojo.Feedback;
import cn.ncu.newmedia.backschool.pojo.Student;
import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/29 13:38
 * @description
 */
@Alias("ApplyVo")
public class ApplyVo extends Apply {

    private Student student;

    private Activity activity;

    private Feedback feedback;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return super.toString()+"ApplyVo{" +
                "student=" + student +
                ", activity=" + activity +
                ", feedback=" + feedback +
                '}';
    }
}
