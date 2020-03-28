package cn.ncu.newmedia.backschool.pojo;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
    private int applyId;

    /**
     * 申请的创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 申请的状态
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private ApplyStatus status;


    /**
     * 申请的描述
     */
    private String description;


    /**
     * 申请学生的id
     */
    private String studentId;


    /**
     * 申请活动的id
     *
     */
    private int activityId;


    private Feedback feedback;

    public Apply() {
    }

    public int getApplyId() {
        return applyId;
    }

    public void setApplyId(int applyId) {
        this.applyId = applyId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "applyId=" + applyId +
                ", createTime=" + createTime +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", studentId=" + studentId +
                ", activityId=" + activityId +
                ", feedback=" + feedback +
                '}';
    }
}
