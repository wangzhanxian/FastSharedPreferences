package com.jeremy.fastsharedpreferences;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by liaohailiang on 2018/10/24.
 */
@RunWith(AndroidJUnit4.class)
public class FastSharedPreferencesAndroidTest {

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        FastSharedPreferences.init(appContext);
    }

    @Test
    public void testLoad() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_load");
        assertNotNull(sharedPreferences);
    }

    @Test
    public void testWriteString() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_string");
        sharedPreferences.edit().putString("test_key", "test_value").apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_string");
        assertEquals(sharedPreferences.getString("test_key", ""), "test_value");
    }

    @Test
    public void testWriteInteger() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_integer");
        sharedPreferences.edit().putInt("test_key", 100).apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_integer");
        assertEquals(sharedPreferences.getInt("test_key", -1), 100);
    }

    @Test
    public void testWriteLong() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_long");
        sharedPreferences.edit().putLong("test_key", 100).apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_long");
        assertEquals(sharedPreferences.getLong("test_key", -1), 100);
    }

    @Test
    public void testWriteFloat() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_float");
        sharedPreferences.edit().putFloat("test_key", 100.0f).apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_float");
        assertTrue(sharedPreferences.getFloat("test_key", -1) == 100.0f);
    }

    @Test
    public void testWriteBoolean() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_bool");
        sharedPreferences.edit().putBoolean("test_key", true).apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_bool");
        assertEquals(sharedPreferences.getBoolean("test_key", false), true);
    }

    @Test
    public void testWriteIntegers() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_int");
        for (int i = 0; i < 1000; i++) {
            sharedPreferences.edit().putInt("test_key_" + i, i).apply();
        }
        sleep(200);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_int");
        for (int i = 0; i < 1000; i++) {
            assertEquals(sharedPreferences.getInt("test_key_" + i, -1), i);
        }
    }

    @Test
    public void testWriteCount() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_count");
        sharedPreferences.edit().clear().commit();
        sleep(200);
        for (int i = 0; i < 10000; i++) {
            sharedPreferences.edit().putInt("test_key_" + i, i).apply();
        }
        sleep(1000);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_count");
        int size = sharedPreferences.getAll().size();
        assertEquals(size, 10000);
    }

    @Test
    public void testRemove() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_re");
        sharedPreferences.edit().putString("test_key", "test_value").apply();
        sleep(100);
        sharedPreferences.edit().remove("test_key").apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_re");
        assertEquals(sharedPreferences.contains("test_key"), false);
    }

    @Test
    public void testClear() {
        FastSharedPreferences sharedPreferences = FastSharedPreferences.get("test_write_cl");
        for (int i = 0; i < 1000; i++) {
            sharedPreferences.edit().putInt("test_key_" + i, i).apply();
        }
        sleep(200);
        sharedPreferences.edit().clear().apply();
        sleep(100);
        FastSharedPreferences.clearCache();
        sharedPreferences = FastSharedPreferences.get("test_write_cl");
        assertEquals(sharedPreferences.getAll().size(), 0);
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }
}
