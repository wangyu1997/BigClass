package com.njtech.bigclass.entity;

/**
 * Created by wangyu on 07/04/2017.
 */

public class AddEntity {
    /**
     * error : false
     * msg : ok
     * data : {"id":"14","cid":"8","c_name":"马克思主义与中国特色社会主义概论","aid":"1","a_name":"计算机科学与技术学院","tId":"58e37c3e02392","wifi":"123","teacher":"王宇","place":"厚学312","content":"请同学们准时到","number":0,"time":"周二 1-2节","state":0,"createTime":"2017-04-07 01:58:44"}
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
        /**
         * id : 14
         * cid : 8
         * c_name : 马克思主义与中国特色社会主义概论
         * aid : 1
         * a_name : 计算机科学与技术学院
         * tId : 58e37c3e02392
         * wifi : 123
         * teacher : 王宇
         * place : 厚学312
         * content : 请同学们准时到
         * number : 0
         * time : 周二 1-2节
         * state : 0
         * createTime : 2017-04-07 01:58:44
         */

        private String id;
        private String cid;
        private String c_name;
        private String aid;
        private String a_name;
        private String tId;
        private String wifi;
        private String teacher;
        private String place;
        private String content;
        private int number;
        private String time;
        private int state;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getA_name() {
            return a_name;
        }

        public void setA_name(String a_name) {
            this.a_name = a_name;
        }

        public String getTId() {
            return tId;
        }

        public void setTId(String tId) {
            this.tId = tId;
        }

        public String getWifi() {
            return wifi;
        }

        public void setWifi(String wifi) {
            this.wifi = wifi;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
