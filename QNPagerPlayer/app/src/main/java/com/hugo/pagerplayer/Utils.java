package com.hugo.pagerplayer;

import android.util.Log;

public class Utils {
    public static String getLineNumber(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0) return "==" + -1 + "=="; //
        return "==" + trace[0].getLineNumber() + "==";
    }

    public static void log(String tag, String result) {
        if (Config.IS_DEBUGGING)
            Log.e(tag, "hugo" + result);
    }
}
