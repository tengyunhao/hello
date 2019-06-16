package com.codem.hello.jenkins.jenkins;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

/**
 * @author miaoying
 * @date 4/2/18
 */
public class HttpUtil {
    public static void close(CloseableHttpResponse rsp) {
        if (!StringUtils.isEmpty(rsp)) {
            try {
                EntityUtils.consume(rsp.getEntity());
            } catch (Exception e) {
            }
        }
    }
}
