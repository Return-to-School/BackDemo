package cn.ncu.newmedia.backschool.pojo;

import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:31
 * @description
 */
@Alias("FeedBack")
public class FeedBack {

    private int id;

    private int apply;

    private int level;

    private String filePath;

    public FeedBack() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
