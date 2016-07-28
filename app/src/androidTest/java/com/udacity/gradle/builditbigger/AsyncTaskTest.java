package com.udacity.gradle.builditbigger;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */


@RunWith( AndroidJUnit4.class)
public class AsyncTaskTest {

    @Rule public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);

    private Button mButton;

    @Before public void init()
    {
        mButton = (Button) main.getActivity().findViewById(R.id.button);
        main.getActivity().tellJoke(mButton);
    }

    @Test public void testAsyncTask()
    {
        try
        {
            // Wait for Async task to be ready
            synchronized (this) {
                wait(1000);
            }
        }
        catch ( InterruptedException e )
        {

        }
        assertThat(main.getActivity().getResult(), is(not("")));
    }

}