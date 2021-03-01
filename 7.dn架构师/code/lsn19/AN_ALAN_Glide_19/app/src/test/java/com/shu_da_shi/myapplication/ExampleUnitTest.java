package com.shu_da_shi.myapplication;

import android.provider.Settings;

import com.shu_da_shi.myapplication.glide.Key;
import com.shu_da_shi.myapplication.glide.recycle.Resource;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

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

        String a = new String("1");
        final ReferenceQueue<String> queue = new ReferenceQueue<>();
        WeakReference<String> weakReference = new WeakReference<>(a, queue);
        System.out.println("弱引用1 " + weakReference.get());
        a = null;
        System.gc();
        System.out.println("弱引用2 " + weakReference.get());
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Reference<? extends String> remove = queue.remove();
                    System.out.println("回收掉 " + remove);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}