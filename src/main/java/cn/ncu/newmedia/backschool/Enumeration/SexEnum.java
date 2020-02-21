package cn.ncu.newmedia.backschool.Enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * @author maoalong
 * @date 2020/1/31 19:39
 * @description
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JSONType(serializeEnumAsJavaBean = true)
public enum SexEnum implements BaseEnum{
    FEMALE(0,"女"),
    MALE(1,"男");

    private int code;
    private String desc;

    SexEnum(int value, String desc) {
        this.code = value;
        this.desc = desc;
    }

    public static SexEnum getEnumById(int sex) {
        return sex == FEMALE.code ?FEMALE:MALE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SexEnum{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }

}
