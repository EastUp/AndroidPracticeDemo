package com.east.architect_zenghui.architect_28_okhttp6_download.download;

import android.content.Context;

import com.east.architect_zenghui.architect_28_okhttp6_download.download.db.DaoSupportFactory;
import com.east.architect_zenghui.architect_28_okhttp6_download.download.db.DownloadEntity;
import com.east.architect_zenghui.architect_28_okhttp6_download.download.db.IDaoSupport;

import java.util.List;

/**
 * description:
 * author: Darren on 2017/11/21 18:07
 * email: 240336124@qq.com
 * version: 1.0
 */
final class DaoManagerHelper {
    private final static DaoManagerHelper sManager = new DaoManagerHelper();
    IDaoSupport<DownloadEntity> mDaoSupport;

    private DaoManagerHelper() {

    }

    public static DaoManagerHelper getManager() {
        return sManager;
    }

    public void init(Context context) {
        DaoSupportFactory.getFactory().init(context);
        mDaoSupport = DaoSupportFactory.getFactory().getDao(DownloadEntity.class);
    }

    public void addEntity(DownloadEntity entity) {
        long delete = mDaoSupport.delete("url = ? and threadId = ?", entity.getUrl(), entity.getThreadId() + "");
        long size = mDaoSupport.insert(entity);
    }

    public List<DownloadEntity> queryAll(String url) {
        return mDaoSupport.querySupport().selection("url = ?").selectionArgs(url).query();
    }

    public void remove(String url) {
        mDaoSupport.delete("url = ?", url);
    }
}
