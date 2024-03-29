package com.codem.hello.manager;

import com.codem.hello.constant.PublishStatusEnum;
import com.codem.hello.vo.PublishReadyVo;
import com.codem.hello.vo.PublishMachineVo;
import com.codem.hello.vo.PublishTaskVo;
import com.codem.hello.vo.PublishProjectVo;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.helper.Range;
import com.offbytwo.jenkins.model.*;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Collections2.filter;

@Service
public class PublishProjectManager {

    public List<PublishTaskVo> getPublishProjectTaskList(String appkey) throws IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        JobWithDetails job = jenkins.getJob(appkey);
        List<Build> builds = job.getAllBuilds(Range.build().from(0).to(10));
        List<PublishTaskVo> taskVoList = Lists.newArrayList();
        if (job.isInQueue()) {
            QueueItem queueItem = job.getQueueItem();
            PublishTaskVo lastTaskVo = new PublishTaskVo();
            lastTaskVo.setAppkey(appkey);
//            lastTaskVo.setPublishId(null);
//            lastTaskVo.setPublishByName(getPublishByName(null));
            lastTaskVo.setPublishStatus(PublishStatusEnum.WAITING);
            lastTaskVo.setCodeUrl(getCodeUrl(queueItem.getActions()));
            lastTaskVo.setCodeBranch(getBranchName(queueItem.getActions()));
            taskVoList.add(lastTaskVo);
        }
        for (Build build : builds) {
            BuildWithDetails details = build.details();
            PublishStatusEnum publishStatus = convertPublishStatus(details.getResult());
            PublishTaskVo taskVo = new PublishTaskVo();
            taskVo.setAppkey(appkey);
            taskVo.setPublishId(Long.parseLong(details.getId()));
            taskVo.setPublishByName(getPublishByName(details.getCauses()));
            taskVo.setPublishStatus(publishStatus);
            taskVo.setCodeUrl(getCodeUrl(details.getActions()));
            taskVo.setCodeBranch(getBranchName(details.getActions()));
            taskVo.setCreateTime(new Date(details.getTimestamp()));
            taskVo.setCompleteTime(publishStatus == PublishStatusEnum.PROCESS ? null : new Date(details.getTimestamp() + details.getDuration()));
            taskVoList.add(taskVo);
        }
        return taskVoList;
    }

    public String getPublishProjectTaskLog(String appkey, Integer taskId) throws IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        JobWithDetails job = jenkins.getJob(appkey);

        return job.getBuildByNumber(taskId).details().getConsoleOutputText();

    }

    private String getPublishByName(List<BuildCause> buildCauseList) {
        if (CollectionUtils.isEmpty(buildCauseList)) {
            return null;
        }
        return buildCauseList.get(0).getUserName();
    }


    private String getBranchName(List actions) {
        if (CollectionUtils.isEmpty(actions)) {
            return "";
        }
        // actions is a List<Map<String, List<Map<String, String ..
        // we have a List[i]["causes"] -> List[BuildCause]
        Collection causes = filter(actions, new Predicate<Map<String, Object>>() {
            @Override
            public boolean apply(Map<String, Object> action) {
                return action.containsKey("remoteUrls");
            }
        });
        if (CollectionUtils.isEmpty(causes)) {
            return "";
        }
        LinkedHashMap<String, Object> gitMap = (LinkedHashMap) Lists.newLinkedList(causes).get(0);
        return (String) ((LinkedHashMap) gitMap.get("buildsByBranchName")).keySet().toArray()[0];
    }

    private String getCodeUrl(List actions) {
        if (CollectionUtils.isEmpty(actions)) {
            return "";
        }
        // actions is a List<Map<String, List<Map<String, String ..
        // we have a List[i]["causes"] -> List[BuildCause]
        Collection causes = filter(actions, new Predicate<Map<String, Object>>() {
            @Override
            public boolean apply(Map<String, Object> action) {
                return action.containsKey("remoteUrls");
            }
        });
        if (CollectionUtils.isEmpty(causes)) {
            return "";
        }
        LinkedHashMap<String, Object> gitMap = (LinkedHashMap) Lists.newLinkedList(causes).get(0);
        return (String) ((List) gitMap.get("remoteUrls")).get(0);
    }

    private PublishStatusEnum convertPublishStatus(BuildResult result) {
        if (result == null) {
            return PublishStatusEnum.PROCESS;
        }
        switch (result) {
            case SUCCESS:
                return PublishStatusEnum.SUCCESS;
            default:
                return PublishStatusEnum.UNKNOWN;
        }
    }

    public List<PublishProjectVo> getPublishProjectList() throws URISyntaxException, IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        List<Job> jobs = Lists.newArrayList(jenkins.getJobs().values());

        return jobs.stream().map(job -> {
            BuildWithDetails details;
            try {
                Build build = job.details().getLastBuild();
                details = build.details();
            } catch (IOException e) {
                throw new RuntimeException("");
            }
            PublishProjectVo projectQueryResultVo = new PublishProjectVo();
            projectQueryResultVo.setAppkey(job.getName());
            projectQueryResultVo.setLastPublishByName(getPublishByName(details.getCauses()));
            projectQueryResultVo.setLastPublishTime(new Date(details.getTimestamp()));
            projectQueryResultVo.setLastPublishStatus(PublishStatusEnum.SUCCESS.desc());
            return projectQueryResultVo;
        }).collect(Collectors.toList());
    }

    public PublishReadyVo getPublishDeployReady(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");
        String jobXml = jenkins.getJobXml(appkey);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(jobXml));

        PublishReadyVo detailVo = new PublishReadyVo();
        detailVo.setCodeUrl(getGitUrl(doc));
        detailVo.setCodeBranch(getGitBranch(doc));

        PublishMachineVo machineVo = new PublishMachineVo();
        machineVo.setMachineIp(getMachineIp(doc));
        machineVo.setMachineName(getMachineIp(doc));

        detailVo.setMachineList(Lists.newArrayList(machineVo));

        return detailVo;
    }

    public String publishDeployReady(String appkey) throws IOException {
        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao",  "a476911605");

        JobWithDetails job = jenkins.getJob(appkey);
        int taskId = job.getNextBuildNumber();    /*获取下一次构建的构建编号，可以用于在触发构建前，先记录构建编号。在后续获取指定编号的构建结果*/
        job.build(true);
        job.build(true);  /*执行指定任务的构建操作*/
        return String.valueOf(taskId);
    }

    public PublishReadyVo getPublishProjectDetail(String appkey) throws URISyntaxException, IOException, DocumentException, GitAPIException {

//        CloneCommand cc = Git.cloneRepository().setURI("https://github.com/tengyunhao/hello.git");
//        cc.setDirectory(new File("/Users/tengyunhao/Downloads/java/workspace/hello/git")).call();

        Git git = Git.open( new File("/Users/tengyunhao/Downloads/java/workspace/hello/git/.git") );

        List<String> list = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call().stream().map(Ref::getName).collect(Collectors.toList());

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");
        String jobXml = jenkins.getJobXml(appkey);

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(jobXml));

        PublishReadyVo detailVo = new PublishReadyVo();
        detailVo.setCodeUrl(getGitUrl(doc));
        detailVo.setCodeBranch(getGitBranch(doc));

        PublishMachineVo machineVo = new PublishMachineVo();
        machineVo.setMachineIp(getMachineIp(doc));
        machineVo.setMachineName(getMachineIp(doc));

        detailVo.setMachineList(Lists.newArrayList(machineVo));

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
