package cn.ncu.newmedia.backschool.pojo;

import cn.ncu.newmedia.backschool.Enumeration.SexEnum;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author maoalong
 * @date 2020/1/12 16:37
 * @description
 */
@Alias("Student")
public class Student implements Serializable {

    private int id;

    /**
     * 学生姓名
     */
    private String name;


    /**
     * 学生的性别
     */
    private SexEnum gender;


    /**
     * 学生的学号
     */
    private String studentCard;


    /**
     * 学生所在学院
     */
    private String college;

    /**
     * 班级名称
     */
    private String classname;


    /**
     * 身份证
     */
    private String idCard;


    private String qq;

    /*银行卡*/
    private String bankCard;

    /*电话*/
    private String phone;

    /*邮箱*/
    private String email;

    /*籍贯*/
    private String origin;

    /*毕业高中*/
    private String highSchool;

    /*账号的id*/
    private int user;

    public Student() {
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

    public SexEnum getGender() {
        return gender;
    }

    public void setGender(SexEnum gender) {
        this.gender = gender;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", studentCard='" + studentCard + '\'' +
                ", college='" + college + '\'' +
                ", classname='" + classname + '\'' +
                ", idCard='" + idCard + '\'' +
                ", qq='" + qq + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", origin='" + origin + '\'' +
                ", highSchool='" + highSchool + '\'' +
                ", user=" + user +
                '}';
    }
}
