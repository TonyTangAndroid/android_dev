package info.juanmendez.android.customloader.service;

import android.util.Log;


public class Logging
{
    public static final String TAG = "CUSTOMLOADER";

    public static void print(String content)
    {
        Log.i(TAG, content);
    }

    public static void print(int content)
    {
        Log.i(TAG, String.valueOf(content));
    }
}