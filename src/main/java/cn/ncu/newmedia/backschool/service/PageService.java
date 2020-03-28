package cn.ncu.newmedia.backschool.service;


import cn.ncu.newmedia.backschool.dao.Page;

import java.util.List;
import java.util.function.Function;

/**
 * 分页的业务逻辑，可以实现代码的公用
 * @param <T>
 * @param <R>
 */
public class PageService<T,R>{

    /**
     *
     * @param currPage 当前所需要的页面
     * @param pageSize 页面的大小
     * @param dao 需要用来执行sql的dao
     * @param method 用来获取list的方法
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> Page getPage(int currPage, int pageSize, T dao, Function<T,List<R>> method){

        /*获取所有的数据*/
        List<R> dataList = method.apply(dao);

        Integer toltalCount = dataList.size();
        int fromIndex = (currPage-1)*pageSize;
        int toIndex = fromIndex+pageSize;
        if(toIndex > toltalCount){
            toIndex = toltalCount;
        }
        dataList = dataList.subList(fromIndex,toIndex);

        /*获取数据库中所有的数据的数量*/



        return new Page(currPage,pageSize,toltalCount,dataList);
    }
}
