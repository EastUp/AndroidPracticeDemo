package com.dn_alan.myapplication.db;

import android.database.sqlite.SQLiteDatabase;

public class BaseDaoFactory {
    private static final BaseDaoFactory ourInstance = new BaseDaoFactory();

    private SQLiteDatabase sqLiteDatabase;

    //定义建数据库的路劲
    //建议写到sd卡中，好处，app 删除了，下次再安装的时候，数据还在
    private String sqliteDatabasePath;

    public static BaseDaoFactory getOurInstance(){
        return ourInstance;
    }

    public BaseDaoFactory() {
        //新建数据库
//        Environment.getExternalStorageDirectory() + File.separator
        sqliteDatabasePath = "data/data/com.dn_alan.myapplication/alan.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass){
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseDao;
    }

    public <T extends BaseDao<M>, M>T getBaseDao(Class<T> daoClass, Class<M> entityClass){
        BaseDao baseDao = null;
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T)baseDao;
    }
}
