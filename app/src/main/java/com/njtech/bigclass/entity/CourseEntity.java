package com.njtech.bigclass.entity;

import java.util.List;

/**
 * Created by wangyu on 06/04/2017.
 */

public class CourseEntity {
    /**
     * error : false
     * msg : ok
     * data : [{"key":"D","info":[{"id":"7","name":"多媒体技术及应用","aid":"1","gpa":"3"},{"id":"9","name":"大学体育","aid":"1","gpa":"1"}]},{"key":"H","info":[{"id":"3","name":"汇编语言程序设计","aid":"1","gpa":"3"}]},{"key":"M","info":[{"id":"8","name":"马克思主义与中国特色社会主义概论","aid":"1","gpa":"3"}]},{"key":"S","info":[{"id":"1","name":"数据结构与算法","aid":"1","gpa":"3"},{"id":"2","name":"数字逻辑设计","aid":"1","gpa":"3"},{"id":"4","name":"数值计算方法","aid":"1","gpa":"3"}]},{"key":"Y","info":[{"id":"5","name":"移动应用开发","aid":"1","gpa":"2"}]}]
     */

    private boolean error;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * key : D
         * info : [{"id":"7","name":"多媒体技术及应用","aid":"1","gpa":"3"},{"id":"9","name":"大学体育","aid":"1","gpa":"1"}]
         */

        private String key;
        private List<InfoBean> info;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * id : 7
             * name : 多媒体技术及应用
             * aid : 1
             * gpa : 3
             */

            private String id;
            private String name;
            private String aid;
            private String gpa;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getGpa() {
                return gpa;
            }

            public void setGpa(String gpa) {
                this.gpa = gpa;
            }
        }
    }
}
