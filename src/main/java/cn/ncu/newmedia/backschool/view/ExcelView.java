package cn.ncu.newmedia.backschool.view;

import cn.ncu.newmedia.backschool.service.ExcelExportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelView extends AbstractXlsView {
    private ExcelExportService excelExportService = null;

    public ExcelView(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
                                      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        excelExportService.make(map,workbook,httpServletRequest,httpServletResponse);
    }
}
