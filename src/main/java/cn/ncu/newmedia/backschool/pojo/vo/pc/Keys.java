package cn.ncu.newmedia.backschool.pojo.vo.pc;

import cn.ncu.newmedia.backschool.Enumeration.ApplyStatus;

/**
 * @Des 查询导出的字段
 * @author maoalong
 * @date 2020/2/18 22:25
 * @description
 */
public class Keys {

    private String studentName;

    private String location;

    private String college;

    private String highSchool;

    private ApplyStatus applyStatus;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }



    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public ApplyStatus getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(ApplyStatus applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
