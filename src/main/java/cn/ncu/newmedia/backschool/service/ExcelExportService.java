package cn.ncu.newmedia.backschool.service;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ExcelExportService {
    public void make(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response);
}
