package cn.ncu.newmedia.backschool.pojo;

import cn.ncu.newmedia.backschool.Enumeration.Level;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:31
 * @description
 */
@Alias("Feedback")
public class Feedback {

    private int feedbackId;

    private int apply;


    /**
     * 评价的级别
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Level level;

    /**
     * 反馈文件路径
     */
    private String filePath;

    public Feedback() {
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", apply=" + apply +
                ", level=" + level +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
