package com.dn_alan.myapplication;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
        List<? extends Person> list = new ArrayList<>();

//        Person person1 = list.get(0);
//        Person person = (Person)list.get(0);
//        System.out.printf(person.toString());
//        test(list);

    }

    //super 只能写， 如果是读（读出来的是object(需要强转)）===》  参数传递
    //extends  只能取，不能写    返回值
    public Person test(List<? super Person> list, List<? extends Person> list1){
//        list.add(new MainActivity.Person());
//        MainActivity.Person person = (MainActivity.Person)list.get(0);
//        Log.d("")
        return list1.get(0);
    }

    class Person{
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}