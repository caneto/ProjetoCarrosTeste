package br.com.carlos.projeto.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

public class MyUtil {

    public static String convertPowerOfByte(long valor, String patern) {
        String[] powerOfByte = {"B", "KB", "MB", "GB", "TB"};
        int potencia = 0;
        int proxima = potencia;
        boolean testaPotenciaActual;
        boolean testaPotenciaSeguinte;
        do {
            proxima = potencia + 1;
            testaPotenciaActual = (Math.pow(2, potencia * 10) <= valor);
            testaPotenciaSeguinte = (valor < Math.pow(2, proxima * 10));
            potencia++;

        } while (!(testaPotenciaActual && testaPotenciaSeguinte));

        potencia--;
        DecimalFormat myFormatter = new DecimalFormat(patern);

        return myFormatter.format(valor / Math.pow(2, potencia * 10)) + " " + powerOfByte[potencia];
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String formataString(int num) {
        if (num > 9)
            return String.valueOf(num);
        else
            return "0" + String.valueOf(num);
    }


    public static String getDiaDaSemana(int num) {
        //Dom Seg Ter Qua Qui Sex Sab
        switch (num) {
            case Calendar.SUNDAY:
                return "dom";
            case Calendar.MONDAY:
                return "seg";
            case Calendar.TUESDAY:
                return "ter";
            case Calendar.WEDNESDAY:
                return "qua";
            case Calendar.THURSDAY:
                return "qui";
            case Calendar.FRIDAY:
                return "sex";
            case Calendar.SATURDAY:
                return "sab";

            default:
                break;
        }
        return null;
    }

    public static String getMes(int num) {
        //Dom Seg Ter Qua Qui Sex Sab
        switch (num) {
            case Calendar.JANUARY:
                return "janeiro";
            case Calendar.FEBRUARY:
                return "fevereiro";
            case Calendar.MARCH:
                return "março";
            case Calendar.APRIL:
                return "abril";
            case Calendar.MAY:
                return "maio";
            case Calendar.JUNE:
                return "junho";
            case Calendar.JULY:
                return "julho";
            case Calendar.AUGUST:
                return "agosto";
            case Calendar.SEPTEMBER:
                return "setembro";
            case Calendar.OCTOBER:
                return "outubro";
            case Calendar.NOVEMBER:
                return "novembro";
            case Calendar.DECEMBER:
                return "dezembro";
            default:
                break;
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean exists(String arquivo) {
        File file = new File(arquivo);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean delete(String diretorio) {
        File src = new File(diretorio);
        deleteRecursive(src);
        return true;
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            if (fileOrDirectory.listFiles() != null)
                for (File child : fileOrDirectory.listFiles())
                    deleteRecursive(child);
        fileOrDirectory.delete();
    }

    public static String getPathOld(Context v) {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        String filesDir;
        if (Environment.MEDIA_MOUNTED.equals(state))
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        if (mExternalStorageAvailable && mExternalStorageWriteable)
            filesDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + v.getPackageName() + "/";
        else
            filesDir = v.getFilesDir().getAbsolutePath() + "/" + v.getPackageName() + "/";
        return filesDir;
    }

    public static String getPath(Context v) {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        String filesDir;
        if (Environment.MEDIA_MOUNTED.equals(state))
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        if (mExternalStorageAvailable && mExternalStorageWriteable)
            filesDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + v.getPackageName() + "/";
        else
            filesDir = v.getFilesDir().getAbsolutePath() + "/." + v.getPackageName() + "/";
        return filesDir;
    }

    public static String getPathTmp(Context v) {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        String filesDir;
        if (Environment.MEDIA_MOUNTED.equals(state))
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        if (mExternalStorageAvailable && mExternalStorageWriteable)
            filesDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + v.getPackageName() + "/";
        else
            filesDir = v.getFilesDir().getAbsolutePath() + "/." + v.getPackageName() + "/";
        return filesDir + "tmp/";
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren(AbsListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        double count = ((GridView) listView).getCount();
        double grid = 3;
        int linhas = (int) Math.ceil(count / grid);//arredondamento para cima com numeros quebrados (0.1 = 1, 1.1 = 2)

        for (int i = 0; i < linhas; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

    }

    public static void hideKeyboard(Context context, EditText v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (v.isShown()) {
            v.clearFocus();
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            v.setVisibility(View.GONE);
            v.setText("");
        } else {
            v.setVisibility(View.VISIBLE);
            v.setText("");
            v.requestFocus();
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void showServiceMessage(String result, Context ctx) {
        if (!result.equalsIgnoreCase("")) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        } else {
//            Toast.makeText(ctx, R.string.tente_novamente_em_alguns_instantes, Toast.LENGTH_LONG).show();
        }
    }


    public static void clearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
}
