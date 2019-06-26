package com.codem.hello.manager;

import com.alibaba.fastjson.JSON;
import com.codem.hello.constant.PublishStatusEnum;
import com.codem.hello.vo.ProjectPublishDetailVo;
import com.codem.hello.vo.ProjectPublishMachineVo;
import com.codem.hello.vo.ProjectPublishTaskVo;
import com.codem.hello.vo.ProjectPublishVo;
import com.google.common.collect.Lists;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublishProjectManager {

    public List<ProjectPublishTaskVo> getPublishProjectTaskList(String appkey) throws IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        JobWithDetails job = jenkins.getJob(appkey);

        List<Build> builds = job.getBuilds();

        List<ProjectPublishTaskVo> taskVoList = Lists.newArrayList();
        for (Build build : builds) {
            BuildWithDetails details = build.details();
            ProjectPublishTaskVo taskVo = new ProjectPublishTaskVo();
            taskVo.setAppkey(appkey);
            taskVo.setPublishId(Long.parseLong(details.getId()));
//            taskVo.setPublishByName();
//            taskVo.setPublishStatus(details.getResult());
//            taskVo.setCodeUrl();
//            taskVo.setCodeBranch();
            taskVo.setCreateTime(new Date(details.getTimestamp()));
            taskVo.setCompleteTime(new Date(details.getTimestamp() + details.getDuration()));
            System.out.println(details.getActions());
            System.out.println(details.getCauses().get(0).getUserName());
            taskVoList.add(taskVo);
            break;
        }
        return taskVoList;
    }

    public List<ProjectPublishVo> getPublishProjectList() throws URISyntaxException, IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        List<Job> jobs = Lists.newArrayList(jenkins.getJobs().values());

        return jobs.stream().map(job -> {
            ProjectPublishVo projectPublishVo = new ProjectPublishVo();
            projectPublishVo.setAppkey("hello");
            projectPublishVo.setDescribe("");
            projectPublishVo.setLastPublishByName("");
            projectPublishVo.setLastPublishTime(new Date());
            projectPublishVo.setLastPublishStatus(PublishStatusEnum.SUCCESS);
            return projectPublishVo;
        }).collect(Collectors.toList());
    }

    public ProjectPublishDetailVo getPublishProjectDetail(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {

//        CloneCommand cc = Git.cloneRepository().setURI("https://github.com/tengyunhao/hello.git");
//        cc.setDirectory(new File("/Users/tengyunhao/Downloads/java/workspace/hello/git")).call();

        Git git = Git.open( new File("/Users/tengyunhao/Downloads/java/workspace/hello/git/.git") );

        List<String> list = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call().stream().map(Ref::getName).collect(Collectors.toList());

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");
        String jobXml = jenkins.getJobXml(appkey);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(jobXml));

        ProjectPublishDetailVo detailVo = new ProjectPublishDetailVo();
        detailVo.setCodeUrl(getGitUrl(doc));
        detailVo.setCodeBranch(getGitBranch(doc));

        ProjectPublishMachineVo machineVo = new ProjectPublishMachineVo();
        machineVo.setMachineIp(getMachineIp(doc));
        machineVo.setMachineName(getMachineIp(doc));

        detailVo.setMachineList(Lists.newArrayList(machineVo));
        detailVo.setCodeBranchhList(list);

        return detailVo;
    }

    private String getGitUrl(Document doc) {
        return getElementScm(doc).element("userRemoteConfigs").element("hudson.plugins.git.UserRemoteConfig").element("url").getText();
    }

    private String getGitBranch(Document doc) {
        return getElementScm(doc).element("branches").element("hudson.plugins.git.BranchSpec").element("name").getText();
    }

    private String getMachineIp(Document doc) {
        return doc.getRootElement()
                .element("postbuilders")
                .element("jenkins.plugins.publish__over__ssh.BapSshBuilderPlugin")
                .element("delegate")
                .element("delegate")
                .element("publishers")
                .element("jenkins.plugins.publish__over__ssh.BapSshPublisher")
                .element("configName")
                .getText();
    }

    private Element getElementScm(Document doc) {
        return doc.getRootElement().element("scm");
    }

    /**
     * 发布服务
     * @param appkey 发布项唯一标志
     * @return 本次发布ID
     * @throws IOException
     */
    public String getPublishProjectDeploy(String appkey) throws IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        JobWithDetails job = jenkins.getJob(appkey);
//        job.getNextBuildNumber();    /*获取下一次构建的构建编号，可以用于在触发构建前，先记录构建编号。在后续获取指定编号的构建结果*/
//        job.build(true);                 /*执行指定任务的构建操作*/

        Build build = job.getBuildByNumber(90);  /*获取某任务第一次构建的构建对象*/
        BuildWithDetails buildWithDetails = build.details(); /*子类转型*/
        String log = buildWithDetails.getConsoleOutputHtml(); /*获取构建的控制台输出信息 ，即构建日志*/
        return log;
    }

}
