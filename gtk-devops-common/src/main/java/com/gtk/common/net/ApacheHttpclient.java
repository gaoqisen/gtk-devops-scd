package com.gtk.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ApacheHttpclient {

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(2000)
            .setSocketTimeout(10000).build();

    public static void main(String[] args) {
        // String a = executeGet("http://23.212.180.252:8070/services/RES_SGAJFD/zrrgdjcxx/mSEsy2B2wd-B45Gxo2y4jTvpmx0RiYVWdnDGf9CohyQ/getDataJson?pageNo=1&pageSize=2&search="+ URLEncoder.encode("[{\"ZJHM.eq\":\"522129199112013512\"}]"));
        int a = 1;
        int b;
        List<Integer> list = Arrays.asList(1,5,8,9,6,4,7);
        Arrays.sort(list.toArray());

        ConcurrentMap<String, String> hashMap = new ConcurrentHashMap<String, String>();
        hashMap.put("", "");
    }


    /**
     * GET请求  参数携带在url中
     *
     * @param url
     * @return
     */
    public static String executeGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String result = null;

        try {
            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * POST请求 带参数
     *
     * @param url 请求地址
     * @param map 参数
     * @return
     */
    public static String executePost(String url, Map<String, String> map) {
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = null;
        String result = null;
        try {
            entity = new UrlEncodedFormEntity(params, "UTF-8");
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);

            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * POST请求   不带参数
     *
     * @param url
     * @return
     */
    public static String executePost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * POST请求   携带Json格式的参数
     *
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String postJson(String url, String param) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setConfig(requestConfig);
        try {
            StringEntity stringEntity = new StringEntity(param);
            stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
            httpPost.setEntity(stringEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
