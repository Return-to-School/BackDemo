package cn.ncu.newmedia.backschool.Enumeration;


import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplyStatus implements BaseEnum{
    NOTEXAMINE(0,"未审核"),
    AGREE(1,"通过"),
    DISAGREE(2,"未通过");

    private int code;
    private String desc;

    ApplyStatus(int code, String desc) {
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
