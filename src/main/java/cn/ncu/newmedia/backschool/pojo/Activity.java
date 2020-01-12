package cn.ncu.newmedia.backschool.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author maoalong
 * @date 2020/1/12 16:25
 * @description
 */
@Alias("Activity")
public class Activity {
    private int id;

    private String name;

    private Date applyStartTime;

    private Date applyEndTime;

    private Date feedBackStartTime;

    private Date feedBackEndTime;

    private String creator;

    private Date createTime;

    private String content;

    private String filePath;


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

    public Date getFeedBackStartTime() {
        return feedBackStartTime;
    }

    public void setFeedBackStartTime(Date feedBackStartTime) {
        this.feedBackStartTime = feedBackStartTime;
    }

    public Date getFeedBackEndTime() {
        return feedBackEndTime;
    }

    public void setFeedBackEndTime(Date feedBackEndTime) {
        this.feedBackEndTime = feedBackEndTime;
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

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applyStartTime=" + applyStartTime +
                ", applyEndTime=" + applyEndTime +
                ", feedBackStartTime=" + feedBackStartTime +
                ", feedBackEndTime=" + feedBackEndTime +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
