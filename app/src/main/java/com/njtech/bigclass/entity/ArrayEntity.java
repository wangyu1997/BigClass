package com.njtech.bigclass.entity;

import java.util.List;

/**
 * Created by wangyu on bg3/04/2017.
 */

public class ArrayEntity {
    /**
     * error : false
     * msg : ok
     * data : []
     */

    private boolean error;
    private String msg;
    private List<?> data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
