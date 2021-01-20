package com.east.framelibrary.http;

import android.text.TextUtils;
import android.util.Log;

import com.east.baselibrary.utils.MD5Util;
import com.east.framelibrary.db.DaoSupportFactory;
import com.east.framelibrary.db.IDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 缓存工具类
 * @author: jamin
 * @date: 2020/5/19 18:04
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class CacheDataUtil {

    /**
     * 获取数据
     */
    public static String getCacheResultJson(String url) {
        IDaoSupport<CacheData> dao = DaoSupportFactory.getFactory().getDao(CacheData.class);
        //查询数据
        List<CacheData> cacheDatas = dao.queryList("urlKey = ?", MD5Util.string2MD5(url)).blockingFirst();
        if (cacheDatas.size() > 0) { //如果有数据就返回让先界面显示
            String resultJson = cacheDatas.get(0).getResultJson();
            if (!TextUtils.isEmpty(resultJson))
                return resultJson;
        }
        return null;
    }

    /**
     * 缓存数据
     */
    public static int cacheData(CacheData data) {
        final IDaoSupport<CacheData> dao = DaoSupportFactory.getFactory().
                getDao(CacheData.class);

        List<CacheData> cacheDatas = dao.queryList("urlKey = ?",data.getUrlKey()).blockingFirst();
        Integer number = 0;
        if (cacheDatas.size() > 0){
            number = dao.update(data, "urlKey = ?", data.getUrlKey()).blockingFirst();
            Log.e("TAG", "number --> " + number);
        }else{
            dao.insert(data).blockingFirst();
        }
        return number;
    }
}
