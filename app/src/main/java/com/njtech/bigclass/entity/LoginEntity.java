package com.njtech.bigclass.entity;

/**
 * Created by wangyu on 03/04/2017.
 */

public class LoginEntity {
    /**
     * error : false
     * msg : ok
     * data : {"uid":"58e0c9bc135d2","username":"wyyy","aid":"1","a_name":"计算机科学与技术学院","name":"人不如故","email":"wangyu19970819@qq.com","header":"http:dsads","sex":"1","createTime":"2017-04-02 09:51:56"}
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
         * uid : 58e0c9bc135d2
         * username : wyyy
         * aid : 1
         * a_name : 计算机科学与技术学院
         * name : 人不如故
         * email : wangyu19970819@qq.com
         * header : http:dsads
         * sex : 1
         * createTime : 2017-04-02 09:51:56
         */

        private String uid;
        private String username;
        private String aid;
        private String a_name;
        private String name;
        private String email;
        private String header;
        private String sex;
        private String createTime;

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
}
