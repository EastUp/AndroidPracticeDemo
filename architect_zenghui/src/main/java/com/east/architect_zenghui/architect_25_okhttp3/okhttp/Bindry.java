package com.east.architect_zenghui.architect_25_okhttp3.okhttp;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hcDarren on 2017/11/18.
 */

public interface Bindry {
    long fileLength();

    String mimType();

    String fileName();

    void onWrite(OutputStream outputStream)throws IOException;
}
