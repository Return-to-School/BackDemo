package cn.ncu.newmedia.backschool.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoalong
 * @date 2020/1/12 22:34
 * @description
 */
public class MessageObject {
    /**
     * 复用代码块，返回一个处理结果消息的map
     * @param keys
     * @param values
     * @return
     */
    public static Map<String,Object> dealMap(List<String> keys, List<Object> values){
        Map<String,Object> map = new HashMap<>();

        for(int i = 0;i<keys.size();i++){
            map.put(keys.get(i),values.get(i));
        }

        return map;
    }
}
