package com.codem.hello.jenkins;
 
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.client.util.EncodingUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
 
/**
 * @author chenlang
 * date 2018/5/4
 */
public class JenkinsPromotionClient {

    private JenkinsHttpClient jenkinsHttpClient;
 
    public JenkinsPromotionClient(JenkinsHttpClient jenkinsHttpClient) {
        this.jenkinsHttpClient = jenkinsHttpClient;
    }
 
    /**
     * 获取job的promotion配置文件
     *
     * @param jobName       job名称
     * @param promotionName promotion名称
     * @return
     * @throws IOException
     */
    public Document getJobPromotionXml(String jobName, String promotionName) throws IOException, DocumentException {
        return DocumentHelper.parseText(this.getJobXml(jobName, promotionName));
    }
 
 
    /**
     * 更新job
     *
     * @param jobName
     * @param promotionName
     * @param jobXml
     * @throws IOException
     */
    public void updateJob(String jobName, String promotionName, String jobXml) throws IOException {
        this.jenkinsHttpClient.post_xml(this.toJobBaseUrl(jobName, promotionName) + "/config.xml", jobXml, true);
    }
 
    /**
     * 添加job脚本
     *
     * @param jobName
     * @param jobXml
     * @throws IOException
     */
    public void createJob(String jobName, String promotionName, String jobXml) throws IOException {
        this.jenkinsHttpClient.post_xml(this.toJobBaseUrl(jobName, promotionName) + "/config.xml", jobXml, true);
    }
 
    /**
     * 添加promotion的job
     *
     * @param jobName
     * @param map
     * @throws IOException
     */
    public void createJob(String jobName, Map map) throws IOException {
        this.jenkinsHttpClient.post_form("/job/" + EncodingUtils.encode(jobName) + "/configSubmit?", map, false);
    }
 
    private String getJobXml(String jobName, String promotionName) throws IOException {
        return this.jenkinsHttpClient.get(this.toJobBaseUrl(jobName, promotionName) + "/config.xml");
    }
 
    private String toJobBaseUrl(String jobName, String promotionName) {
        return "/job/" + EncodingUtils.encode(jobName) + "/promotion/process/" + promotionName;
    }
 
    /**
     * promotion脚本的构建
     * @param jobName
     * @param promotionName
     * @param version
     * @param isFirstBuild
     * @throws IOException
     */
    public void build(String jobName,String promotionName,Integer version,boolean isFirstBuild) throws IOException{
        if (isFirstBuild) {
            this.jenkinsHttpClient.post("/job/"+ EncodingUtils.encode(jobName) + "/"+version+"/promotion/forcePromotion?name="+promotionName+"&json=%7B%7D&Submit=Force promotion");
        } else {
            this.jenkinsHttpClient.post("/job/"+ EncodingUtils.encode(jobName) + "/"+version+"/promotion/"+promotionName+"/build?json=%7B%7D&Submit=Re-execute promotion");
        }
    }
 
 
}