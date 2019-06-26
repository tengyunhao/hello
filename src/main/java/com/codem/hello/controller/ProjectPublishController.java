package com.codem.hello.controller;

import com.codem.hello.manager.PublishProjectManager;
import com.codem.hello.vo.ProjectPublishDetailVo;
import com.codem.hello.vo.ProjectPublishTaskVo;
import com.codem.hello.vo.ProjectPublishVo;
import org.dom4j.DocumentException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * 1、新增一个发布项（git地址、appkey、部署机器） 待开发
 *
 * 2、编辑一个发布项（发布项ID、Git地址、AppKey、部署机器）
 *
 * 3、删除一个发布项（发布项ID）
 *
 * 4、ok 查询发布项列表
 *
 * 5、ok 查询发布项明细（Git地址及分支、AppKey、部署机器列表） jenkins接口支持，细节待完善
 *
 * 6、ok 发布服务
 *
 * 7、发布项的任务列表（appkey、分支、部署机器、）
 *
 * 8、发布项的任务日志
 */
@RestController
public class ProjectPublishController {

    @Resource
    private PublishProjectManager publishProjectManager;

    @RequestMapping(value = "/publish")
    public String publish() {
        return "";
    }

    @RequestMapping(value = "/project/list")
    public List<ProjectPublishVo> getPublishProjectList() throws URISyntaxException, IOException {
        return publishProjectManager.getPublishProjectList();
    }

    @RequestMapping(value = "/project/deploy/config")
    public ProjectPublishDetailVo getPublishProjectDetail(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return publishProjectManager.getPublishProjectDetail(appkey);
    }

    @RequestMapping(value = "/project/deploy/start")
    public String getPublishProjectDeploy(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return publishProjectManager.getPublishProjectDeploy(appkey);
    }

    @RequestMapping(value = "/project/task/list")
    public List<ProjectPublishTaskVo> getPublishProjectTaskList(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return publishProjectManager.getPublishProjectTaskList(appkey);
    }

    @RequestMapping(value = "/project/task/detail")
    public String getPublishProjectTaskDetail(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return "";
    }

    @RequestMapping(value = "/project/deploy/{appkey}/log/{id}")
    public String getPublishProjectLog(@PathVariable String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return "";
    }

}
