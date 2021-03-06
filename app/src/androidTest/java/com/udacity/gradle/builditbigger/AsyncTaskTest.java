package com.udacity.gradle.builditbigger;


import android.os.Build;
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
            // We have to wait so much time because this test is run together with
            // app engine run and stop sto we need some extra seconds.
            synchronized (this) {
                //Older versions take more time to load async task
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
                    wait(2000);
                else
                    wait(4000);
            }
        }
        catch ( InterruptedException e )
        {

        }
        assertThat(main.getActivity().getResult(), is(not("")));
    }

}