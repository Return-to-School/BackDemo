package cn.ncu.newmedia.backschool.Enumeration;

public enum Role {
    SUPERMANAGER("superManager"),
    GROUPMANAGER("groupManager"),
    USER("user");

    private String desc;

    Role(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
