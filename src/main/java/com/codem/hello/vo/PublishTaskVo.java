package com.codem.hello.vo;

import com.codem.hello.constant.PublishStatusEnum;

import java.util.Date;

public class PublishTaskVo {

    private String appkey;

    private Long publishId;

    private String publishByName;

    private PublishStatusEnum publishStatus;

    private String codeUrl;

    private String codeBranch;

    private Date createTime;

    private Date completeTime;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public String getPublishByName() {
        return publishByName;
    }

    public void setPublishByName(String publishByName) {
        this.publishByName = publishByName;
    }

    public PublishStatusEnum getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(PublishStatusEnum publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getCodeBranch() {
        return codeBranch;
    }

    public void setCodeBranch(String codeBranch) {
        this.codeBranch = codeBranch;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
}
