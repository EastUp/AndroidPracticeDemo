package com.east.architect_zenghui.architect_28_okhttp6_download.download;

import java.io.File;
import java.io.IOException;

/**
 * Created by hcDarren on 2017/11/26.
 */

public interface DownloadCallback {
    void onFailure(IOException e);

    void onSucceed(File file);
}
