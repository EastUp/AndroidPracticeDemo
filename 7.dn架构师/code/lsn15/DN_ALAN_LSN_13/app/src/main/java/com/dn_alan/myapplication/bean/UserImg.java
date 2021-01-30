package com.dn_alan.myapplication.bean;

import com.dn_alan.myapplication.annotation.DbTable;

@DbTable("tb_img")
public class UserImg {
    private String time;
    private String imgPath;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "UserImg{" +
                "time='" + time + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
