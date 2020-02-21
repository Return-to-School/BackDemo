package cn.ncu.newmedia.backschool.Utils;

/**
 * @author maoalong
 * @date 2020/1/31 22:30
 * @description
 */

import cn.ncu.newmedia.backschool.Enumeration.BaseEnum;

/** 枚举工具类 */
public class EnumUtils {

    /** 通过code获取枚举*/
    public static <T extends Enum<T>> T getEnumByCode(Class<T> enumType, Integer code) {
        for (T each : enumType.getEnumConstants()) {
            BaseEnum baseEnum = null;
            if(each instanceof BaseEnum)
                baseEnum= (BaseEnum)each;
            if(code.equals(baseEnum.getCode())){
                return  each;
            }
        }
        return null;
    }
}
