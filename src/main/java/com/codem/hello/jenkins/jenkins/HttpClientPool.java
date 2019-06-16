package com.codem.hello.jenkins.jenkins;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author miaoying
 * @date 4/2/18
 */
public class HttpClientPool {
    private final static int MAX_PERROUTE = 500;
    private final static int MAX_TOTAL = 500;

    public static CloseableHttpClient getHttpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(MAX_TOTAL);
        cm.setDefaultMaxPerRoute(MAX_PERROUTE);
        CloseableHttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }
}
