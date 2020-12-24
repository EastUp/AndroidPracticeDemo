package com.example.wuyang.lsn4code;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.wuyang.lsn4code", appContext.getPackageName());

        MyObjectPool pool=new MyObjectPool(2,4);
        Object o1=pool.acquire();
        Object o2=pool.acquire();
//        Object o3=pool.acquire();
//        Object o4=pool.acquire();

        System.out.println(o1.hashCode()+"");
        System.out.println(o2.hashCode()+"");


    }
}
