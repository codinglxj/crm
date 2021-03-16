package com.bjpowernode.crm.vo;

import java.util.List;

/**
 * @authot : lxj
 * @Date : 2021/3/16 14:26
 */
public class PaginationVO<T> {
    private Integer total;
    private List<T> dataList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
