package cn.ncu.newmedia.backschool.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author maoalong
 * @date 2020/1/12 16:34
 * @description
 */
@Alias("Apply")
public class Apply {

    private int id;

    private Date createTime;

    private int  status;

    private String origin;

    private String highSchool;

    private String description;

    private int studentId;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
