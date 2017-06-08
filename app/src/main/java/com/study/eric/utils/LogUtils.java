package com.study.eric.utils;

import android.util.Log;

public class LogUtils {
    private final String TAG = "test";

    public static void d(String msg) {
        Log.d("test", msg);
    }

    public static void e(String msg) {
        Log.e("test", msg);
    }
}
