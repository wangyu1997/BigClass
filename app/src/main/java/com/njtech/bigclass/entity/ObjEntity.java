package com.njtech.bigclass.entity;

/**
 * Created by wangyu on bg3/04/2017.
 */

public class ObjEntity {
    /**
     * error : false
     * msg : ok
     * data : {}
     */

    private boolean error;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
