package cn.ncu.newmedia.backschool.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author maoalong
 * @date 2020/1/12 16:25
 * @description
 */
@Alias("Activity")
public class Activity implements Serializable {

    /**
     * 活动id
     */
    private int activityId;


    /**
     * 活动名称
     */
    private String name;


    /**
     * 活动开始申请的时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyStartTime;

    /**
     * 活动结束申请的时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyEndTime;

    /*
    *反馈开始的时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackStartTime;

    /*反馈结束的时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackEndTime;

    /*活动创建者的姓名*/
    private String creator;


    /*活动的创建时间*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /*活动内容*/
    private String content;

    /*活动资料的路径*/
    private String filePath;

    /*活动的区域*/
    private String location;

    /*是否需要审核*/
    private Boolean needExamine;


    public Activity() {
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(Date applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Date getFeedbackStartTime() {
        return feedbackStartTime;
    }

    public void setFeedbackStartTime(Date feedbackStartTime) {
        this.feedbackStartTime = feedbackStartTime;
    }

    public Date getFeedbackEndTime() {
        return feedbackEndTime;
    }

    public void setFeedbackEndTime(Date feedbackEndTime) {
        this.feedbackEndTime = feedbackEndTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getNeedExamine() {
        return needExamine;
    }

    public void setNeedExamine(Boolean needExamine) {
        this.needExamine = needExamine;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", applyStartTime=" + applyStartTime +
                ", applyEndTime=" + applyEndTime +
                ", feedbackStartTime=" + feedbackStartTime +
                ", feedbackEndTime=" + feedbackEndTime +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", filePath='" + filePath + '\'' +
                ", location='" + location + '\'' +
                ", needExamine=" + needExamine +
                '}';
    }

}
