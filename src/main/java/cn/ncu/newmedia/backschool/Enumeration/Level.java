package cn.ncu.newmedia.backschool.Enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author maoalong
 * @date 2020/1/31 23:02
 * @description
 */
@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  Level implements BaseEnum{
    UNQUALIFIED(0,"不合格"),
    QUALIFIED(1,"合格"),
    EXCELLENT(2,"优秀"),
    UNPROCESSED(3,"未处理");

    private int code;
    private String desc;

    Level(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
