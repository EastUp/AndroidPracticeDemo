package com.east.architect_zenghui.architect_28_okhttp6_download.download;

import android.content.Context;

import java.io.File;

/**
 * Created by hcDarren on 2017/11/26.
 */

final class FileManager {
    private File mRootDir;
    private Context mContext;
    private static final FileManager sManager = new FileManager();

    public static FileManager manager() {
        return sManager;
    }

    public void init(Context context){
        this.mContext = context.getApplicationContext();
    }

    public void rootDir(File file){
        if(!file.exists()){
            file.mkdirs();
        }

        if(file.exists()&&file.isDirectory()){
            mRootDir = file;
        }
    }

    /**
     * 通过网络的路径获取一个本地文件路径
     * @param url
     * @return
     */
    public File getFile(String url) {
        String fileName = Utils.md5Url(url);
        if(mRootDir == null){
            mRootDir = mContext.getCacheDir();
        }
        File file = new File(mRootDir,fileName);
        return file;
    }
}
