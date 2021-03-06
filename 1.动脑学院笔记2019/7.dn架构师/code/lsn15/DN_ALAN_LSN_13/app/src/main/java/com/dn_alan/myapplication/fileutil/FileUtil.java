package com.dn_alan.myapplication.fileutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件操作
 */

public class FileUtil {

    /**
     * 复制单个文件(可更名复制)
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名(注：目录路径需带文件名)
     * @return
     */
    public static void CopySingleFile(String oldPathFile, String newPathFile) {
        try {
//            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            File newFile=new File(newPathFile);
            File parentFile=newFile.getParentFile();
            if(!parentFile.exists())
            {
                parentFile.mkdirs();
            }
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
//                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.flush();
                fs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
