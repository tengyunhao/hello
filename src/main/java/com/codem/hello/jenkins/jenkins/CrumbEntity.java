package com.codem.hello.jenkins.jenkins;

import java.io.Serializable;

/**
 * @author miaoying
 * @date 4/2/18
 */
public class CrumbEntity implements Serializable {
    private String crumb;
    private String crumbRequestField;

    public String getCrumb() {
        return crumb;
    }

    public void setCrumb(String crumb) {
        this.crumb = crumb;
    }

    public String getCrumbRequestField() {
        return crumbRequestField;
    }

    public void setCrumbRequestField(String crumbRequestField) {
        this.crumbRequestField = crumbRequestField;
    }
}