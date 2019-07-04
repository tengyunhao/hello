package com.codem.hello.vo;

import java.util.List;

public class PublishReadyVo {

    private String codeUrl;

    private String codeBranch;

    private List<PublishMachineVo> machineList;

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

    public List<PublishMachineVo> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<PublishMachineVo> machineList) {
        this.machineList = machineList;
    }
}
