package cn.ncu.newmedia.backschool.pojo;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author maoalong
 * @date 2020/1/12 16:34
 * @description
 */
@Alias("Apply")
public class Apply {

    /**
     * 申请id
     */
    private int id;

    /**
     * 申请的创建时间
     */
    private Date createTime;

    /**
     * 申请的状态
     */
    private ApplyStatus status;

    /**
     * 学生的生源地
     */
    private String origin;

    /**
     * 毕业高中
     */
    private String highSchool;

    /**
     * 申请的描述
     */
    private String description;


    /**
     * 申请学生的id
     */
    private int studentId;


    /**
     * 申请活动的id
     *
     */
    private int activityId;

    public Apply() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ApplyStatus getStatus() {
        return status;
    }

    public void setStatus(ApplyStatus status) {
        this.status = status;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", status=" + status +
                ", origin='" + origin + '\'' +
                ", highSchool='" + highSchool + '\'' +
                ", description='" + description + '\'' +
                ", studentId=" + studentId +
                ", activityId=" + activityId +
                '}';
    }
}
