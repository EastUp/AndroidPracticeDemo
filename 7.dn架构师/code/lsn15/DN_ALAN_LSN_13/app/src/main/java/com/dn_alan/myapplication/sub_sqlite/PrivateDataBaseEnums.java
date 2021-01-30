package com.dn_alan.myapplication.sub_sqlite;

import android.os.Environment;

import com.dn_alan.myapplication.bean.User;
import com.dn_alan.myapplication.db.BaseDaoFactory;

import java.io.File;

public enum  PrivateDataBaseEnums {
    DATEBASE("");  //实例对象
    private String value;  //属性
    PrivateDataBaseEnums(String value) {
    }

    //用于产生路径
    public String getValue(){
        UserDao userDao = BaseDaoFactory.getOurInstance().getBaseDao(UserDao.class, User.class);
        if(userDao != null){
            User currentUser = userDao.getCurrentUser();
            if(currentUser != null){
//                File file = new File("data/data/com.dn_alan.myapplication");
//                if(!file.exists()){
//                    file.mkdirs();
//                }
//                //path = data/data/com.dn_alan.myapplication/N001_login.db
//                return file.getAbsolutePath() + "/" + currentUser.getId() + "_login.db";
                //建议写入SD卡
                File file=new File(Environment.getExternalStorageDirectory(),"update/"+currentUser.getId());
                if(!file.exists())
                {
                    file.mkdirs();
                }

                return file.getAbsolutePath()+"/login.db";
            }
        }
        return null;
    }


}
