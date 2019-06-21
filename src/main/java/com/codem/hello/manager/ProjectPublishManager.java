package com.codem.hello.manager;

import com.codem.hello.vo.ProjectPublishDetailVo;
import com.google.common.collect.Lists;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ProjectPublishManager {

    public Object getPublishProjectList() throws URISyntaxException, IOException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");

        List<Job> jobs = Lists.newArrayList(jenkins.getJobs().values());

        return jenkins.getJobs();
    }

    public ProjectPublishDetailVo getPublishProjectDetail() throws URISyntaxException, IOException, DocumentException, GitAPIException {

        JenkinsServer jenkins = new JenkinsServer(URI.create("http://47.105.223.154:8080"), "tengyunhao", "a476911605");
        String jobXml = jenkins.getJobXml("hello");

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new StringReader(jobXml));

        ProjectPublishDetailVo detailVo = new ProjectPublishDetailVo();
        detailVo.setGitUrl(getGitUrl(doc));
        detailVo.setGitBranch(getGitBranch(doc));
        detailVo.setMachineName(getMachineIp(doc));
        detailVo.setMachineIp(getMachineIp(doc));


        Git git = new Git(new FileRepository("https://github.com/tengyunhao/hello.git"));
        
        ListBranchCommand listBranchCommand = git.branchList();
        for (Ref ref : listBranchCommand.call()) {
            System.out.println(ref.getName());
        }

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


}
