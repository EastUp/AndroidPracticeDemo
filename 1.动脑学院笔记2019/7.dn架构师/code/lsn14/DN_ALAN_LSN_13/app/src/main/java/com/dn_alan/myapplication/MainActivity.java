package com.dn_alan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dn_alan.myapplication.bean.Person;
import com.dn_alan.myapplication.bean.User;
import com.dn_alan.myapplication.db.BaseDao;
import com.dn_alan.myapplication.db.BaseDaoFactory;
import com.dn_alan.myapplication.db.BaseDaoNewImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insert(View view) {
        BaseDao baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
        baseDao.insert(new User(1, "alan", "123"));

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
}
