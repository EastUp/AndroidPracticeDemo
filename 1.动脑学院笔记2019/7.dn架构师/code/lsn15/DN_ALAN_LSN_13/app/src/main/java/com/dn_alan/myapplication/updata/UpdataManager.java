package com.dn_alan.myapplication.updata;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.dn_alan.myapplication.bean.User;
import com.dn_alan.myapplication.db.BaseDaoFactory;
import com.dn_alan.myapplication.fileutil.FileUtil;
import com.dn_alan.myapplication.sub_sqlite.UserDao;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UpdataManager {
    private static final String INFO_FILE_DIV = "/";
    private File parentFile = new File(Environment.getExternalStorageDirectory(), "update");
    private List<User> userList;
    private File bakFile = new File(parentFile, "backDb");

    public UpdataManager() {
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!bakFile.exists()) {
            bakFile.mkdirs();
        }
    }

    /**
     * 检测当前数据库版本中的数据库表
     *
     * @param context
     */
    public void checkThisVersionTable(Context context) {
        //获取User表中的数据
        UserDao userDao = BaseDaoFactory.getOurInstance().getBaseDao(UserDao.class, User.class);
        userList = userDao.query(new User());

        //读取xml 文件的信息
        UpdateDbXml updateDbXml = readDbXml(context);

        //获取当前最新版本
        String thisVersion = "V003";  //应该文件中读取
        Log.d("alanLog", "thisVersion" + thisVersion);

        //得到要更新的版本
        CreateVersion createVersion = analyseCreateVersion(updateDbXml, thisVersion);

        try {
            execuCreateVersion(createVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据建表脚本，核实一遍应该存在的表（折行一次建表）
    private void execuCreateVersion(CreateVersion createVersion) throws Exception {
        if (createVersion == null || createVersion.getCreateDbs() == null) {
            throw new Exception("createVersion or createDbs is null;");
        }

        for (CreateDb createDb : createVersion.getCreateDbs()) {
            if (createDb == null || createDb.getName() == null) {
                throw new Exception("db or dbName is null when createVersion;");
            }
            if (!"login".equals(createDb.getName())) {
                continue;
            }

            //获取xml文件中sql 语句
            List<String> sqlCreates = createDb.getSqlCreates();
            SQLiteDatabase sqLiteDatabase = null;

            //如果user表中，存在有数据（也就是有登录过用户）
            if (userList != null && !userList.isEmpty()) {
                //多用户建新表
                for (int i = 0; i < userList.size(); i++) {
                    //创建好数据库
                    sqLiteDatabase = getDb(createDb, userList.get(i).getId());

                    /**
                     * 执行xml 文件中 SqlCreates sql 语句（新建表）
                     */
                    executeSql(sqLiteDatabase, sqlCreates);
                }
            }

            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
    }

    //执行sql 语句
    private void executeSql(SQLiteDatabase sqLiteDatabase, List<String> sqlCreates) {
        if (sqlCreates == null || sqlCreates.size() == 0) {
            return;
        }

        //开启事务， 保证这个代码同事执行多条sql语句
        sqLiteDatabase.beginTransaction();

        try {
            for (String sqlCreate : sqlCreates) {
                sqlCreate.replace("\r\n", "");
                sqlCreate.replace("\n", "");
                if (!sqlCreate.trim().equals("")) {
                    sqLiteDatabase.execSQL(sqlCreate);
                }
            }
            //标记数据库事务执行成功
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    private SQLiteDatabase getDb(UpdateDb db, String id) {
        return getDb(db.getDbName(), id);
    }

    private SQLiteDatabase getDb(CreateDb createDb, String id) {
        return getDb(createDb.getName(), id);
    }

    private SQLiteDatabase getDb(String dbName, String id) {
        String dbfilepath = null;
        SQLiteDatabase sqLiteDatabase = null;

        File file = new File(parentFile, id);
        if (!file.exists()) {
            file.mkdirs();
        }

        //xml 文件中是否存在login
        if (dbName.equalsIgnoreCase("login")) {
            dbfilepath = file.getAbsolutePath() + "/login.db";  //拼接login.db数据库,路径
        } else {
            dbfilepath = parentFile + "/user.db"; //拼接数据库,路径 （如用户）
        }

        //拼接好，路径，生成各个文件目录
        if (dbfilepath != null) {
            File file1 = new File(dbfilepath);
            file1.mkdirs();
            if (file1.isDirectory()) {
                file1.delete();
            }
            //创建数据库
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbfilepath, null);
        }
        return sqLiteDatabase;
    }

    /**
     * 解析出对应版本的建表脚本
     *
     * @param updateDbXml
     * @param thisVersion
     */
    private CreateVersion analyseCreateVersion(UpdateDbXml updateDbXml, String thisVersion) {
        CreateVersion createVersion = null;
        if (updateDbXml == null || thisVersion == null) {
            return createVersion;
        }

        List<CreateVersion> createVersions = updateDbXml.getCreateVersions();

        if (createVersions != null) {
            for (CreateVersion version : createVersions) {
                String[] strVersion = version.getVersion().trim().split(",");
                for (int i = 0; i < strVersion.length; i++) {
                    //通过thisVersion，xml文件中是否存在当前客户端更新的版本。
                    if (strVersion[i].trim().equalsIgnoreCase(thisVersion)) {
                        //如果存在，方法xml中的版本号 如（v003）
                        createVersion = version;
                        break;
                    }

                }
            }
        }
        return createVersion;
    }

    private UpdateDbXml readDbXml(Context context) {
        InputStream is = null;
        Document document = null;
        try {
            is = context.getAssets().open("updateXml.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (document == null) {
            return null;
        }
        UpdateDbXml xml = new UpdateDbXml(document);
        return xml;
    }


    /**
     * 写入版本信息
     *
     * @param nerVersion 模拟当前客户端最新数据库的版本
     * @return 保存成功返回true, 否则false
     */
    public boolean saveVersionInfo(String nerVersion) {

        boolean ret = false;
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(parentFile, "update.txt"), false);
            writer.write(nerVersion + INFO_FILE_DIV + "V002");//v004/v003/v002/v001;
            writer.flush();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public void startUpdataDb(Context context) {
        UpdateDbXml updateDbXml = readDbXml(context);
        if (getLocalVersionInfo()) {
            //拿到当前版本
            String thisVersion = this.existVersion;
            //拿去上一个版本
            String lastVersion = this.lastBackupVersion;

            UpdateStep updateStep = analyseUpdataStep(updateDbXml, lastVersion, thisVersion);

            if (updateStep == null) {
                return;
            }

            //获取更新用的对象
            List<UpdateDb> updateDbs = updateStep.getUpdateDbs();

            CreateVersion createVersion = analyseCreateVersion(updateDbXml, thisVersion);

            //备份每一个用户的db 文件
            for (User user : userList) {
                String loginDbDir = parentFile.getAbsolutePath() + "/" + user.getId() + "/login.db";
                String loginCopy = bakFile.getAbsolutePath() + "/" + user.getId() + "/login.db";
                /**
                 * 原理，把loginDbDir文件中 db文件，拷贝到loginCopy文件中一份
                 */
                FileUtil.CopySingleFile(loginDbDir, loginCopy);
            }

            //备份user(总)数据库
            String user = parentFile.getAbsolutePath() + "/user.db";
            String user_bak = bakFile.getAbsolutePath() + "/user.db";
            FileUtil.CopySingleFile(user, user_bak);


            try {
                //第二步：执行sql_beore语句
                executeDb(updateDbs, -1);


                //第三部：检查新表，创建新表
//                execuCreateVersion(createVersion);

                //第四部：从备份中恢复数据，恢复后删除备份表
                executeDb(updateDbs, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 第五步:升级成功，删除备份数据库
            if (userList != null && !userList.isEmpty()) {
                for (User user1 : userList) {
                    String logicDbDir = bakFile.getAbsolutePath() + "/" + user1.getId() + "/login.db";
                    File file = new File(logicDbDir);
                    if (file.exists()) {
                        file.delete();
                    }

                }
            }

            File userFileBak = new File(user_bak);
            if (userFileBak.exists()) {
                userFileBak.delete();
            }

            Log.i("alanLog", "升级成功");
        }
    }

    //执行针对db升级的sql集合
    private void executeDb(List<UpdateDb> updateDbs, int type) throws Exception {
        if (updateDbs == null) {
            throw new Exception("updateDbs is null;");
        }
        for (UpdateDb db : updateDbs) {
            if (db == null || db.getDbName() == null) {
                throw new Exception("db or dbName is null;");
            }

            List<String> sqls = null;
            //更改表
            if (type < 0) {
                sqls = db.getSqlBefores();
            } else if (type > 0) {
                sqls = db.getSqlAfters();
            }

            SQLiteDatabase sqlitedb = null;

            try {
                // 逻辑层数据库要做多用户升级
                if (userList != null && !userList.isEmpty()) {
                    // 多用户表升级
                    for (int i = 0; i < userList.size(); i++) {
                        sqlitedb = getDb(db, userList.get(i).getId());

                        executeSql(sqlitedb, sqls);

                        sqlitedb.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != sqlitedb) {
                    sqlitedb.close();
                }
            }
        }
    }

    private UpdateStep analyseUpdataStep(UpdateDbXml updateDbXml,
                                         String lastVersion, String thisVersion) {
        if (lastVersion == null || thisVersion == null) {
            return null;
        }

        // 更新脚本
        UpdateStep thisStep = null;
        if (updateDbXml == null) {
            return null;
        }

        List<UpdateStep> steps = updateDbXml.getUpdateSteps();
        if (steps == null || steps.size() == 0) {
            return null;
        }

        for (UpdateStep step : steps) {
            if (step.getVersionFrom() == null || step.getVersionTo() == null) {

            } else {
                // 升级来源以逗号分隔
                String[] lastVersionArray = step.getVersionFrom().split(",");

                if (lastVersionArray != null && lastVersionArray.length > 0) {
                    for (int i = 0; i < lastVersionArray.length; i++) {
                        // 有一个配到update节点即升级数据
                        /**
                         * 比方：xml文件 versionFrom="V002" versionTo="V003"
                         *       txt文件 V003/V002
                         *       匹配成功
                         *
                         *       xml文件 versionFrom="V001" versionTo="V003"
                         *       txt文件 V003/V002
                         *       匹配不成功
                         *
                         * 所以，如果存在数据库版本跳跃， 需要添加多条匹配，
                         * 只有匹配到对应的，才能获取到sql 语句，对数据进程备份。
                         */
                        if (lastVersion.equalsIgnoreCase(lastVersionArray[i]) &&
                                step.getVersionTo().equalsIgnoreCase(thisVersion)) {
                            thisStep = step;

                            break;
                        }
                    }
                }
            }
        }

        return thisStep;

    }

    /**
     * 获取本地版本相关信息
     *
     * @return 获取数据成功返回true，否则返回false
     * @see
     */
    private String existVersion;
    private String lastBackupVersion;

    private boolean getLocalVersionInfo() {
        boolean ret = false;

        File file = new File(parentFile, "update.txt");

        if (file.exists()) {
            int byteread = 0;
            byte[] tempbytes = new byte[100];
            StringBuilder stringBuilder = new StringBuilder();
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                while ((byteread = in.read(tempbytes)) != -1) {
                    stringBuilder.append(new String(tempbytes, 0, byteread));
                }
                String[] infos = stringBuilder.toString().split(INFO_FILE_DIV);
                if (infos.length == 2) {
                    existVersion = infos[0];
                    lastBackupVersion = infos[1];
                    ret = true;
                }
            } catch (Exception e) {

            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    in = null;
                }
            }
        }

        return ret;
    }

}
