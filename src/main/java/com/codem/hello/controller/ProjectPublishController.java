package com.codem.hello.controller;

import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * 1、新增一个发布项（git地址、appkey、部署机器） 待开发
 *
 * 2、编辑一个发布项（发布项ID、Git地址、AppKey、部署机器）
 *
 * 3、删除一个发布项（发布项ID）
 *
 * 4、查询发布项列表 ok
 *
 * 5、查询发布项明细（Git地址及分支、AppKey、部署机器列表） jenkins接口支持，细节待完善
 *
 * 6、发布服务 ok
 *
 * 7、实时查询发布状态
 *
 * 8、实时查询发布日志
 */
@RestController
public class ProjectPublishController {


    @RequestMapping(value = "/publish")
    public String publish() {
        return "";
    }

    @RequestMapping(value = "/project/list")
    public Object getPublishProjectList() throws URISyntaxException, IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        return jenkins.getJobs();
    }

    @RequestMapping(value = "/project/detail")
    public Object getPublishProjectDetail() throws URISyntaxException, IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        return jenkins.getJob("hello");
    }

}
