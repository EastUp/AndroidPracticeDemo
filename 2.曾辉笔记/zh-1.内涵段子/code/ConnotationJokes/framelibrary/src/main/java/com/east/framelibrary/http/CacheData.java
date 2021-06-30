package com.east.framelibrary.http;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 缓存实体类
 *  @author: jamin
 *  @date: 2020/5/19
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class CacheData {

    //请求链接
    private String urlKey;

    //后台返回的json
    private String resultJson;

    public CacheData() {
    }

    public CacheData(String urlKey, String resultJson) {
        this.urlKey = urlKey;
        this.resultJson = resultJson;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
}
