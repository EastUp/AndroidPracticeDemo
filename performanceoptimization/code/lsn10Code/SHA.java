package com.example.administrator.lsn_10_demo;

import org.apache.commons.codec.digest.Sha2Crypt;
import org.junit.Test;

public class SHA {
    @Test
    public void test(){
        String result=Sha2Crypt.sha256Crypt("jett".getBytes());
        System.out.println(result);
    }
}
