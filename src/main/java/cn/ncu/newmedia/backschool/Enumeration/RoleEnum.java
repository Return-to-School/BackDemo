package cn.ncu.newmedia.backschool.Enumeration;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleEnum implements BaseEnum{
    SUPERMANAGER(0,"superManager"),
    GROUPMANAGER(1,"groupManager"),
    USER(2,"user");

    private int code;
    private String desc;

    RoleEnum(int code, String desc) {
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
