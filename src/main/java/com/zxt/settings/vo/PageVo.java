package com.zxt.settings.vo;

import com.zxt.settings.domain.Activity;

import java.util.List;

public class PageVo<T> {

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

    @Override
    public String toString() {
        return "PageVo{" +
                "total=" + total +
                ", dataList=" + dataList +
                '}';
    }
}
