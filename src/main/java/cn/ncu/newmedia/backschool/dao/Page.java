package cn.ncu.newmedia.backschool.dao;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 * @author maoalong
 * @date 2020/2/2 19:51
 * @description
 */
public class Page implements Serializable {

    private static int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

    private List data; // 当前页中存放的记录,类型一般为List

    private long totalCount; // 总记录数

    private long pageNo;

    /**
     * 构造方法，只构造空页.
     */
    public Page() {
        this(1, 0, DEFAULT_PAGE_SIZE, new ArrayList());
    }

    /**
     * 默认构造方法.
     * @param totalSize 数据库中总记录条数
     * @param pageSize  本页容量
     * @param data	  本页包含的数据
     */
    public Page(long pageNo, int pageSize, long totalSize ,  List data){
        this.pageSize = pageSize;
        this.totalCount = totalSize;
        this.pageNo = pageNo;
        this.data = data;
    }

    /**
     * 取总记录数.
     */
    public long getTotalCount() {
        return this.totalCount;
    }


    /**
     * 取总页数.
     */
    public long getTotalPageCount() {
        if (totalCount % pageSize == 0)
            return totalCount / pageSize;
        else
            return totalCount / pageSize + 1;
    }

    /**
     * 取每页数据容量.
     */
    public int getPageSize() {
        return pageSize;
    }


    public long getPageNo(){ return pageNo; }

    /**
     * 取当前页中的记录.
     */
    public List getResult() {
        return data;
    }

    /**
     * 该页是否有下一页.
     */
    public boolean isHasNextPage() {
        return this.pageNo < this.getTotalPageCount();
    }

    /**
     * 该页是否有上一页.
     */
    public boolean isHasPreviousPage() {
        return this.pageNo > 1;
    }

}
