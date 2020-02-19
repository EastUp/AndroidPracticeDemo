package com.east.architect_zenghui.architect12_Designmode5_tempolate;

import androidx.annotation.NonNull;

/**
 * Created by hcDarren on 2017/10/1.
 */

public class Request implements Runnable, Comparable<Request> {
    @Override
    public void run() {
        System.out.println("run");
    }

    @Override
    public int compareTo(@NonNull Request o) {
        return 0;
    }
}
