package com.codem.hello.manager;

import com.codem.hello.jenkins.JobDto;
import com.codem.hello.jenkins.jenkins.JenkinsBuildService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectManager {

    public List<JobDto> list() {

        JenkinsBuildService jenkinsBuildService = new JenkinsBuildService();
        String jobName = "hello";
        Map<String, String> map = new HashMap<>();
        return jenkinsBuildService.list();
    }

}
