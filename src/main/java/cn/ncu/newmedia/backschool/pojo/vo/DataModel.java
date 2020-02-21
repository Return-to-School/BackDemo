package cn.ncu.newmedia.backschool.pojo.vo;

import com.alibaba.fastjson.JSONArray;

/**
 * @author maoalong
 * @date 2020/1/29 23:53
 * @description
 */
public class DataModel {
    private String[] header;
    private JSONArray dataList;

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public JSONArray getDataList() {
        return dataList;
    }

    public void setDataList(JSONArray dataList) {
        this.dataList = dataList;
    }
}
