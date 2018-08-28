package com.eleven.jpromiser;

import com.eleven.jpromiser.core.JPromiser;
import com.eleven.jpromiser.core.ThreadJPromiser;
import com.eleven.jpromiser.core.ThreadPromiser;

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

    public void foo(){
        ThreadPromiser $q = new ThreadPromiser() {

            @Override
            protected void threadAync(ThreadJPromiser<String, String> d) {
                d.reject();
            }
        };
    }
}