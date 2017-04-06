package com.njtech.bigclass.entity;

public class DataBean {
    /**
     * uid : 58e0c9bc135d2
     * username : wyyy
     * aid : 1
     * a_name : 计算机科学与技术学院
     * name : 人不如故
     * email : wangyu19970819@qq.com
     * header : http:dsads
     * sex : 1
     * createTime : 2017-04-bg2 09:51:56
     */

    private String uid = "";
    private String username = "";
    private String aid = "";
    private String a_name = "";
    private String name = "";
    private String email = "";
    private String header = "";
    private String sex = "";
    private String createTime = "";


    public DataBean(DataBean dataBean) {
        this.uid = dataBean.getUid();
        this.username = dataBean.getUsername();
        this.aid = dataBean.getAid();
        this.a_name = dataBean.getA_name();
        this.name = dataBean.getName();
        this.email = dataBean.getEmail();
        if (dataBean.getHeader() != null)
            this.header = dataBean.getHeader();
        this.sex = dataBean.getSex();
        this.createTime = dataBean.getCreateTime();
    }


    public DataBean() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}