package cn.ncu.newmedia.backschool.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maoalong
 * @date 2020/1/12 16:25
 * @description
 */
@Alias("Activity")
public class Activity implements Serializable {
    private int id;

    private String name;

    private Date applyStartTime;

    private Date applyEndTime;

    private Date feedbackStartTime;

    private Date feedbackEndTime;

    private String creator;

    private Date createTime;

    private String content;

    private String filePath;

    private String location;

    private Boolean needExamine;

    public Activity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
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
