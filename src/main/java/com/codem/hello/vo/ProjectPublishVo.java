package com.codem.hello.vo;

import java.util.Date;

public class ProjectPublishVo {

    private String appkey;

    private String describe;

    private String lastPublishByName;

    private Date lastPublishTime;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLastPublishByName() {
        return lastPublishByName;
    }

    public void setLastPublishByName(String lastPublishByName) {
        this.lastPublishByName = lastPublishByName;
    }

    public Date getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(Date lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }
}
