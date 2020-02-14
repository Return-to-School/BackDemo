package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.pojo.vo.DataModel;
import cn.ncu.newmedia.backschool.service.ExcelExportService;
import cn.ncu.newmedia.backschool.view.ExcelView;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author maoalong
 * @date 2020/1/29 23:59
 * @description
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {


    /**
     * 用excel的形式导出信息
     * @param dataModel
     * @return
     */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public ModelAndView export(@RequestBody DataModel dataModel){

        ModelAndView mv = new ModelAndView();

        View view = new ExcelView(exportApplyExcelService());
        mv.setView(view);
        mv.addObject("dataModel",dataModel);
        return mv;
    }



    @SuppressWarnings("unchecked")
    private ExcelExportService exportApplyExcelService(){
        return ((map, workbook, request, response) -> {
            try{

                response.setHeader("Content-Disposition","attachment; filename="+
                        new String("列表.xlsx".getBytes(),"iso8859-1"));
                response.setContentType("application/vnd.ms-excel");

                HSSFSheet sheet = (HSSFSheet)workbook.createSheet("applyVo");
                int rowNum = 0;
                HSSFRow header = sheet.createRow(rowNum++);

                DataModel dataModel = (DataModel) map.get("dataModel");

                /*创建表头*/
                for(int i = 0; i<dataModel.getHeader().length; i++) {
                    header.createCell(i).setCellValue(dataModel.getHeader()[i]);
                }

                for(int i=0;i<dataModel.getDataList().size();i++) {

                    HSSFRow row = sheet.createRow(rowNum++);
                    JSONObject obj = dataModel.getDataList().getJSONObject(i);
                    for(int j = 0; j<dataModel.getHeader().length; j++){
                        row.createCell(j).setCellValue(obj.get(dataModel.getHeader()[j]).toString());
                    }

                }

            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
