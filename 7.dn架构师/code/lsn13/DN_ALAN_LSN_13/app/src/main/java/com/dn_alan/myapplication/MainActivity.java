package com.dn_alan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dn_alan.myapplication.bean.Person;
import com.dn_alan.myapplication.bean.User;
import com.dn_alan.myapplication.db.BaseDao;
import com.dn_alan.myapplication.db.BaseDaoFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void insert(View view) {
        BaseDao<User> baseDao = BaseDaoFactory.getOurInstance().getBaseDao(User.class);
        baseDao.insert(new User(1, "alan", "123"));

        BaseDao<Person> personBaseDao = BaseDaoFactory.getOurInstance().getBaseDao(Person.class);
        personBaseDao.insert(new Person(1, "alan_person", "123"));

        Toast.makeText(this, "执行成功！", Toast.LENGTH_SHORT).show();
    }
}
