package com.readr.ro.countries;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.readr.ro.countries.util.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Domnica on 11/2/2016.
 */
@RunWith(AndroidJUnit4.class)
public class UtilityClassesTest {

    private Context mContext;

    @Before
    public void useAppContext() throws Exception {
        // Initialize a mock context
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testIsCallingCodeCorrect() {
        boolean result1 = Utils.isCallingCodeCorrect(null);
        assertEquals(false, result1);

        boolean result2 = Utils.isCallingCodeCorrect(new String[]{"", "", ""});
        assertEquals(false, result2);

        boolean result3 = Utils.isCallingCodeCorrect(new String[]{"", "321"});
        assertEquals(true, result3);
    }

    @Test
    public void testFetchFlagFromAlphaCode() {
        int result1 = Utils.fetchFlagFromAlphaCode("ro", "", mContext);
        assertEquals(true, result1 != 0);

        int result2 = Utils.fetchFlagFromAlphaCode("", "", mContext);
        assertEquals(false, result2 != 0);

        int result3 = Utils.fetchFlagFromAlphaCode("", "ro", mContext);
        assertEquals(true, result3 != 0);
    }
}
