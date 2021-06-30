package com.example.wuyang.lsn4code;

import org.junit.Test;

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
    }
    @Test
    public void test() {

        long start = System.currentTimeMillis();
        float sum = 0;
        for (float i = 0; i < 1000000; i++) {
            sum += i;
        }
        System.out.println("time=" + (System.currentTimeMillis() - start));

    }
}