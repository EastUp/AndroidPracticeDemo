package com.dn_alan.myapplication.sub_sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dn_alan.myapplication.db.BaseDao;
import com.dn_alan.myapplication.db.BaseDaoFactory;

public class BaseDaoSubFactory extends BaseDaoFactory {

    private static final BaseDaoSubFactory ourInstance = new BaseDaoSubFactory();
    public static BaseDaoSubFactory getInstance(){
        return ourInstance;
    }

    //定义一个用于实现分库的数据库操作对象
    private SQLiteDatabase sqLiteDatabase;




    public <T extends BaseDao<M>, M>T getSubDao(Class<T> daoClass, Class<M> entityClass){
        BaseDao baseDao = null;
        if(map.get(PrivateDataBaseEnums.DATEBASE.getValue()) != null){
            return (T)map.get(PrivateDataBaseEnums.DATEBASE.getValue());
        }
        Log.e("alanLog", "生成数据库文件的位置" + PrivateDataBaseEnums.DATEBASE.getValue());
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(PrivateDataBaseEnums.DATEBASE.getValue(), null );
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            map.put(PrivateDataBaseEnums.DATEBASE.getValue(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T)baseDao;
    }

}
