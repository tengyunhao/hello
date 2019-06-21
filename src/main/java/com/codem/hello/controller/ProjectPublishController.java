package com.codem.hello.controller;

import com.codem.hello.manager.ProjectPublishManager;
import com.codem.hello.vo.ProjectPublishDetailVo;
import org.dom4j.DocumentException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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

    @Resource
    private ProjectPublishManager projectPublishManager;

    @RequestMapping(value = "/publish")
    public String publish() {
        return "";
    }

    @RequestMapping(value = "/project/list")
    public Object getPublishProjectList() throws URISyntaxException, IOException {
        return projectPublishManager.getPublishProjectList();
    }

    @RequestMapping(value = "/project/detail")
    public ProjectPublishDetailVo getPublishProjectDetail() throws URISyntaxException, IOException, DocumentException, GitAPIException {
        return projectPublishManager.getPublishProjectDetail();
    }

}
