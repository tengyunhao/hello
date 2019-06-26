package com.codem.hello.vo;

import java.util.List;

public class ProjectPublishDetailVo {

    private String codeUrl;

    private String codeBranch;

    private List<String> codeBranchhList;

    private List<ProjectPublishMachineVo> machineList;

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

    public List<String> getCodeBranchhList() {
        return codeBranchhList;
    }

    public void setCodeBranchhList(List<String> codeBranchhList) {
        this.codeBranchhList = codeBranchhList;
    }

    public List<ProjectPublishMachineVo> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<ProjectPublishMachineVo> machineList) {
        this.machineList = machineList;
    }
}
