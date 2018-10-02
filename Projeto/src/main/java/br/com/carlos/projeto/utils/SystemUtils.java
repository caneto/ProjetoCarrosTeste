package br.com.carlos.projeto.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class SystemUtils {

    private SystemUtils() {
    }

    public static String getVersionName(Context context) {
        String versionName = "0";
        try {
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                versionName = packageManager.getPackageInfo(packageName, 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        if (activity != null) {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public static void hideKeyboardSingle(AppCompatActivity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
