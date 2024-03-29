package com.ju.mall.Common.api;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @projectName: mall
 * @package: com.ju.mall.Common.api
 * @className: CommonPage
 * @author: Eric
 * @description: TODO 分页对象的封装
 * @date: 2023/5/28 16:56
 * @version: 1.0
 */
public class CommonPage<T>{

    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     *总页数
     */
    private Integer totalPage;
    /**
     * 总数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> list;

    public static<T> CommonPage<T> restPage(List<T> data){
        CommonPage<T> commonPage = new CommonPage<>();
        //从list中获取分页信息
        PageInfo<T> pageInfo = new PageInfo<>(data);
        commonPage.setPageNum(pageInfo.getPageNum());
        commonPage.setPageSize(pageInfo.getPageSize());
        commonPage.setTotalPage(pageInfo.getPages());
        commonPage.setTotal(pageInfo.getTotal());
        commonPage.setList(data);
        return commonPage;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
