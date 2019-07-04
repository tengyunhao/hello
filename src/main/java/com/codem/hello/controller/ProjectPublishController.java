package com.codem.hello.controller;

import com.codem.hello.manager.PublishProjectManager;
import com.codem.hello.vo.PublishReadyVo;
import com.codem.hello.vo.PublishTaskVo;
import com.codem.hello.vo.PublishProjectVo;
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
 1、查询项目列表
 请求：
    http://localhost:8123/project/list
 返回：
     [
        {
            "appkey": "hello",
            "lastPublishStatus": "SUCCESS",
            "lastPublishByName": "tengyunhao",
            "lastPublishTime": "2019-06-22T10:43:08.244+0000"
        },
        {
            "appkey": "hello",
            "lastPublishStatus": "SUCCESS",
            "lastPublishByName": null,
            "lastPublishTime": "1970-01-01T00:00:00.000+0000"
        }
     ]

 2、查询发布准备信息
 请求：
    http://localhost:8123/project/deploy/ready?appkey=com.codem.platform.deployer
 返回：
    {
        "codeUrl": "https://github.com/tengyunhao/hello.git",
        "codeBranch": "*\/master",
        "machineList": [
            {
                "machineIp": "121.42.145.183",
                "machineName": "121.42.145.183",
                "lastPublishTime": null
            }
        ]
    }

 3、开始发布服务
 请求：
    http://localhost:8123/project/deploy/start?appkey=com.codem.platform.deployer
 返回：
    任务ID

 4、任务部署日志


 5、查询项目任务列表
 请求：
    http://localhost:8123/project/task/list?appkey=com.codem.platform.deployer
 返回：
     [
         {
             "appkey": "com.codem.platform.deployer",
             "publishId": 1,
             "publishByName": "tengyunhao",
             "publishStatus": "UNKNOWN",
             "codeUrl": "https://github.com/tengyunhao/hello.git",
             "codeBranch": "refs/remotes/origin/master",
             "createTime": "2019-06-30T09:40:01.963+0000",
             "completeTime": "2019-06-30T09:41:22.708+0000"
         }
     ]

 *
 *
 * 1、新增一个发布项（git地址、appkey、部署机器） 待开发
 *
 * 2、编辑一个发布项（发布项ID、Git地址、AppKey、部署机器）
 *
 * 3、删除一个发布项（发布项ID）
 *
 *
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
    public List<PublishProjectVo> getPublishProjectList() throws URISyntaxException, IOException {
        return publishProjectManager.getPublishProjectList();
    }

    @RequestMapping(value = "/project/deploy/ready")
    public PublishReadyVo getPublishProjectReady(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return publishProjectManager.getPublishDeployReady(appkey);
    }

    @RequestMapping(value = "/project/deploy/start")
    public String getPublishProjectDeploy(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return publishProjectManager.publishDeployReady(appkey);
    }

    @RequestMapping(value = "/project/task/log")
    public String getPublishProjectTaskLog(String appkey, Integer taskId) throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return publishProjectManager.getPublishProjectTaskLog(appkey, taskId);
    }

    @RequestMapping(value = "/project/task/list")
    public List<PublishTaskVo> getPublishProjectTaskList(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {
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
