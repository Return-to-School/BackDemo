package cn.ncu.newmedia.backschool.pojo;

import cn.ncu.newmedia.backschool.Enumeration.Level;
import org.apache.ibatis.type.Alias;

/**
 * @author maoalong
 * @date 2020/1/12 16:31
 * @description
 */
@Alias("Feedback")
public class Feedback {

    private int id;

    private int apply;

    private Level level;

    private String filePath;

    public Feedback() {
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
                "id=" + id +
                ", apply=" + apply +
                ", level=" + level +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
