package com.codem.hello.jenkins;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.Map;

/**
 * @author chenlang
 * date 2018/5/7
 */
public class JenkinsPromotionUtils {
 
 
    private static final String SUB_PATH_PROMOTION_COOMMAND = "hudson.plugins.promoted__builds.PromotionProcess";
    private static final String SUB_PATH_BUILD = "/buildSteps";
    private static final String SUB_PATH_BUILDER_SHELL_COMMAND = "/hudson.tasks.Shell/command";
    private static final String PATH_PROMOTION_COMMAND = SUB_PATH_PROMOTION_COOMMAND + SUB_PATH_BUILD + SUB_PATH_BUILDER_SHELL_COMMAND;
    private static String CREATE_PROMOTION_JSON = "{'properties':{'stapler-class-bag':'true','hudson-plugins-promoted_builds-JobPropertyImpl':{'promotions':{'activeItems':{'name':'%s','isVisible':'','icon':'star-gold','hasAssignedLabel':false,'assignedLabelString':'','conditions':{'stapler-class-bag':'true'}}}}}}";
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
 
 
    public static void updatePromotionShell(Document jobConfigDocument, String jobName, JenkinsPromotionClient jenkinsPromotionClient, String promotionShell, String path) throws IOException, DocumentException {
        if (StringUtils.isBlank(promotionShell)) {
            return;
        }
        String promotionName = getPromotionName(jobConfigDocument, path);
        Document document = jenkinsPromotionClient.getJobPromotionXml(jobName, promotionName);
        document.selectSingleNode(PATH_PROMOTION_COMMAND).setText(promotionShell);
        jenkinsPromotionClient.updateJob(jobName, promotionName, document.asXML());
    }
 
    public static void createPromotionShell(Document jobConfigDocument, String tmpJobName, String jobName, String promotionShell, String path, JenkinsPromotionClient jenkinsPromotionClient) throws IOException, DocumentException {
        if (StringUtils.isBlank(promotionShell)) {
            return;
        }
        String promotionName = getPromotionName(jobConfigDocument, path);
        Document document = jenkinsPromotionClient.getJobPromotionXml(tmpJobName, promotionName);
        document.selectSingleNode(PATH_PROMOTION_COMMAND).setText(promotionShell);
        Map<String, String> map = Maps.newHashMap();
        map.put("Content-Type", CONTENT_TYPE);
        map.put("json", String.format(CREATE_PROMOTION_JSON, promotionName));
        try {
            jenkinsPromotionClient.createJob(jobName, map);
        } catch (Exception e) {
        }
        jenkinsPromotionClient.createJob(jobName, promotionName, document.asXML());
    }
 
    public static String getPromotionName(Document jobConfigDocument, String path) {
        return jobConfigDocument.selectSingleNode(path).getText();
    }
 
}