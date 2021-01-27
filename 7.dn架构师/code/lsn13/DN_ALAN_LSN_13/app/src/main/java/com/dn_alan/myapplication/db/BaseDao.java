package com.dn_alan.myapplication.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.dn_alan.myapplication.annotation.DbField;
import com.dn_alan.myapplication.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseDao<T> implements IBaseDao<T> {
    private static final String TAG = "dn_alan_13";
    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;

    //表名
    private String tableName;

    //持有操作数据库所对应的java类型
    private Class<T> entityClass;

    //标记：用来表示是否做过初始化操作
    private boolean isInit = false;

    //定义一个缓存空间（key-字段名   value-成员变量）
    private HashMap<String, Field> cacheMap;


    //架构内部的逻辑，最后不要提供构造方法给调用层使用
    protected void init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;

        //可以根据传入的entityClass类型来建表，只需要建一次
        if (!isInit) {
            //自动建表
            //取得表明
            if (entityClass.getAnnotation(DbTable.class) == null) {
                //反射到类名
                this.tableName = entityClass.getSimpleName();
            } else {
                //取得注解上的名字
                this.tableName = entityClass.getAnnotation(DbTable.class).value();
            }

            //执行建表操作
            //create table if not exists
            // tb_user(_id integer, name varchar(20), password varchar(20))
            //使用getCreateTableSql（） 生成sql语句
            String createTableSql = getCreateTableSql();
            Log.i(TAG, createTableSql);
            this.sqLiteDatabase.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
    }

    private void initCacheMap() {
        //1、取得所有字段名
        String sql = "select * from " + tableName + " limit 1";  //空表
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        //2、取所有的成员变量
        Field[] declaredFields = entityClass.getDeclaredFields();
        //把所有字段的访问权限打开
        for (Field f : declaredFields) {
            f.setAccessible(true);
        }

        //字段 跟  成员变量一一对应
        for (String columNage : columnNames) {
            Field columnField = null;
            for (Field field : declaredFields) {
                String fieldName = "";  //对象中的成员变量名字
                if (field.getAnnotation(DbField.class) != null) {
                    fieldName = field.getAnnotation(DbField.class).value();
                } else {
                    fieldName = field.getName();
                }

                if (columNage.equals(fieldName)) {  //匹配
                    columnField = field;
                    break;
                }
            }

            if (columnField != null) {
                cacheMap.put(columNage, columnField);
            }
        }
    }

    private String getCreateTableSql() {
        //create table if not exists
        // tb_user(_id integer, name varchar(20), password varchar(20),)
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName + "(");
        //反射得到所有的成员变量
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Class type = field.getType();// 拿到成员的类型

            if (field.getAnnotation(DbField.class) != null) {
                //通过注解获取
                if (type == String.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " TEXT,");
                } else if (type == Integer.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " INTEGER,");
                } else if (type == Long.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BIGINT,");
                } else if (type == Double.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BLOB,");
                } else {
                    //不支持的类型
                    continue;
                }
            } else {
                //通过反射获取
                if (type == String.class) {
                    stringBuffer.append(field.getName() + " TEXT,");
                } else if (type == Integer.class) {
                    stringBuffer.append(field.getName() + " INTEGER,");
                } else if (type == Long.class) {
                    stringBuffer.append(field.getName() + " BIGINT,");
                } else if (type == Double.class) {
                    stringBuffer.append(field.getName() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getName() + " BLOB,");
                } else {
                    //不支持的类型
                    continue;
                }
            }
        }

        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    @Override
    public long insert(T entity) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("_id", 1);
//        contentValues.put("name", "alan");
//        contentValues.put("password", "123");
//        sqLiteDatabase.insert(tableName, null, contentValues);
        //1、准本好ContentValues中需要的数据
        Map<String, String> map = getValues(entity);

        //2、把数据转移到ContentValues
        ContentValues values = getContentValues(map);
        //3、开始插入
        sqLiteDatabase.insert(tableName, null, values);
        return 0;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }


    //key(字段)--- value(成员变量)
    private Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        //返回的是所有的成员变量
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next();
            field.setAccessible(true);

            try {
                //获取对象的属性值
                Object obj = field.get(entity);
                if (obj == null) {
                    continue;
                }

                String value = obj.toString();

                String key = "";
                if (field.getAnnotation(DbField.class) != null) {
                    key = field.getAnnotation(DbField.class).value();
                } else {
                    key = field.getName();
                }

                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    @Override
    public long updata(T entity, T where) {
        return 0;
    }

    @Override
    public int delete(T where) {
        return 0;
    }

    @Override
    public List<T> query(T where) {
        return null;
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        return null;
    }
}
