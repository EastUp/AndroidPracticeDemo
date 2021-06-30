package com.dn_alan.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dn_alan.myapplication.bean.Person;
import com.dn_alan.myapplication.bean.User;
import com.dn_alan.myapplication.bean.UserImg;
import com.dn_alan.myapplication.db.BaseDao;
import com.dn_alan.myapplication.db.BaseDaoFactory;
import com.dn_alan.myapplication.db.BaseDaoNewImpl;
import com.dn_alan.myapplication.db.PhotoDao;
import com.dn_alan.myapplication.sub_sqlite.BaseDaoSubFactory;
import com.dn_alan.myapplication.sub_sqlite.UserDao;
import com.dn_alan.myapplication.updata.UpdataManager;

import java.util.Date;
import java.util.List;


/**
 * cd storage/emulated/0/update/N001
 */
public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private UpdataManager updataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updataManager = new UpdataManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
    }

    public void insert(View view) {
        BaseDao baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
        baseDao.insert(new User("1", "alan", "123"));

        BaseDao personBaseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
        personBaseDao.insert(new Person(2, "alan_person", "123"));

        Toast.makeText(this, "执行成功！", Toast.LENGTH_SHORT).show();
    }

    public void clickUpdate(View view) {
        BaseDaoNewImpl baseDao = BaseDaoFactory.getOurInstance().getBaseDao(
                BaseDaoNewImpl.class, Person.class);
        Person person = new Person();
        person.setName("jett");
        Person where = new Person();
        where.setId(1);
        long updata = baseDao.updata(person, where);
        Toast.makeText(this, "执行成功！", Toast.LENGTH_SHORT).show();
    }

    public void clickDelete(View view) {
        BaseDaoNewImpl baseDao = BaseDaoFactory.getOurInstance().getBaseDao(
                BaseDaoNewImpl.class, Person.class);
        Person where = new Person();
        where.setName("jett");
        where.setId(1);
        int delete = baseDao.delete(where);
        Toast.makeText(this, "执行成功！", Toast.LENGTH_SHORT).show();
    }

    public void clickSelect(View view) {
        BaseDaoNewImpl baseDao = BaseDaoFactory.getOurInstance().getBaseDao(
                BaseDaoNewImpl.class, Person.class);
        Person where = new Person();
        where.setId(1);
        List<Person> query = baseDao.query(where);
        Log.i("dn_alan", query.size() + "");
    }

    public void clickLogin(View view) {
        User user = new User();
        user.setName("alan" + (++i));  //alan1  alan2  alan3
        user.setPassword("123456");
        user.setId("N00" + i);   //N001  N002  N003_login.db
        UserDao userDao = BaseDaoFactory.getOurInstance().getBaseDao(UserDao.class, User.class);
        userDao.insert(user);
        Toast.makeText(this, "执行成功！", Toast.LENGTH_SHORT).show();
    }

    public void clickSubInsert(View view) {
        UserImg img = new UserImg();
        img.setTime(new Date().toString());
        img.setImgPath("www.baidu.com/123123123.png");

        PhotoDao subDao = BaseDaoSubFactory.getInstance().getSubDao(PhotoDao.class, UserImg.class);
        subDao.insert(img);
        Toast.makeText(this, "执行成功！", Toast.LENGTH_SHORT).show();
    }

    public void write(View view) {
        boolean saveVersionInfo = updataManager.saveVersionInfo("V003");
        if(saveVersionInfo)
            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
    }

    public void update(View view) {
        updataManager.checkThisVersionTable(this);
        updataManager.startUpdataDb(this);
    }
}
