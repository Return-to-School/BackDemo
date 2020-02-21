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
     * @param action1 用来获取list的方法
     * @param action2 用来获取数据库记录数量的方法
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> Page getPage(int currPage, int pageSize, T dao, Function<T,List<R>> action1, Function<T,Integer> action2){

        /*获取分页的数据*/
        List<R> dataList = action1.apply(dao);
        /*获取数据库中所有的数据的数量*/
        Integer toltalCount = action2.apply(dao);

        return new Page(currPage,pageSize,toltalCount,dataList);
    }
}
