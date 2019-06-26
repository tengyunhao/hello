package com.codem.hello.vo;

import com.codem.hello.constant.PublishStatusEnum;

import java.util.Date;

public class ProjectPublishVo {

    private String appkey;

    private String describe;

    private PublishStatusEnum lastPublishStatus;

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

    public PublishStatusEnum getLastPublishStatus() {
        return lastPublishStatus;
    }

    public void setLastPublishStatus(PublishStatusEnum lastPublishStatus) {
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
