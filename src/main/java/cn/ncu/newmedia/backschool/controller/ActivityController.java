package cn.ncu.newmedia.backschool.controller;

import cn.ncu.newmedia.backschool.Utils.MessageObject;
import cn.ncu.newmedia.backschool.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/13 21:24
 * @description
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    private final String ACTVITYFILEPATH = "e:/activityFile/";

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/upload/{id}")
    @ResponseBody
    public Map<String, Object> upload(@PathVariable("id")int id, MultipartFile activityFile){

        String filename = System.currentTimeMillis()+activityFile.getOriginalFilename();

        String creator = activityService.findCreatorById(id);

        File director = new File(ACTVITYFILEPATH+creator);

        if(!director.exists())
            director.mkdir();

        File file = new File(director+"/"+filename);

        try{
            activityFile.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
            return MessageObject.dealMap(List.of("success"),List.of(false));
        }

        return MessageObject.dealMap(List.of("success"),List.of(true));
    }
}
