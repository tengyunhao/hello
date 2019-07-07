package com.codem.hello.vo;

import java.util.Date;

public class PublishProjectVo {

    private String appkey;

    private String lastPublishStatus;

    private String lastPublishByName;

    private Date lastPublishTime;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getLastPublishStatus() {
        return lastPublishStatus;
    }

    public void setLastPublishStatus(String lastPublishStatus) {
        this.lastPublishStatus = lastPublishStatus;
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
