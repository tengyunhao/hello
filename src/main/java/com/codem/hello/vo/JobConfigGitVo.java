package com.codem.hello.vo;

public class JobConfigGitVo {

    private String remoteUrl;

    private String remoteCredentialsId;

    private String branch;

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getRemoteCredentialsId() {
        return remoteCredentialsId;
    }

    public void setRemoteCredentialsId(String remoteCredentialsId) {
        this.remoteCredentialsId = remoteCredentialsId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
