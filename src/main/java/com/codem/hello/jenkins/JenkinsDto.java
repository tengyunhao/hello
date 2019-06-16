package com.codem.hello.jenkins;

import java.util.List;

public class JenkinsDto {

    private List<JobDto> jobs;

    public List<JobDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDto> jobs) {
        this.jobs = jobs;
    }
}
