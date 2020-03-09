package com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp;

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
