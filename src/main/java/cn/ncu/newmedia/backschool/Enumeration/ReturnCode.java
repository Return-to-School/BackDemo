package cn.ncu.newmedia.backschool.Enumeration;


import cn.ncu.newmedia.backschool.pojo.vo.response.ResponseWrapper;
import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;

@JSONType(serializeEnumAsJavaBean = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReturnCode implements BaseEnum{

    PARAMS_ERROR (334,"参数格式错误"),
    FAILED (444,"操作失败"),
    NODATA (202,"数据库无记录"),
    ACTIVITY_EXISTS(555,"活动重名"),
    FILE_UPLOAD_ERROR(337,"文件上传错误"),
    SUCCESS (0,"操作成功"),
    REPEAT_OPERATION(121,"请勿重复操作"),
    NO_PROFILE(600,"个人资料未完善") ,
    TIME_ERROR(700,"请在规定时间范围内操作"),
    USER_NOT_EXISTS (401,"用户不存在"),
    PASSWORD_WRONG(402,"密码错误"),
    STUDENT_NOT_EXISTS(701,"该学生不存在"),
    IDCARD_ERROR(702,"身份证校验失败"),
    ID_NOT_MATCH_NAME(703,"学号姓名不匹配");

    private int code;
    private String msg;

    ReturnCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return msg;
    }
}
