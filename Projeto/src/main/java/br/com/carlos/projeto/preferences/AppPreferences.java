package br.com.carlos.projeto.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.carlos.projeto.utils.SystemUtils;

public class AppPreferences {
    private static final String mPreferencesName = "br.com.golmobile.sis";
    private Context mContext;

    public AppPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public static AppPreferences newInstance(Context context) {
        return new AppPreferences(context);
    }

    protected SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(mPreferencesName, Context.MODE_MULTI_PROCESS);
    }

    public String getLastVersionName() {
        String version = getPreferences().getString("LastVersionName", null);
        if (version == null) {
            version = SystemUtils.getVersionName(mContext);
            setLastVersionName(version);
        }
        return version;
    }

    public void setLastVersionName(String lastVersionName) {
        getPreferences().edit().putString("LastVersionName", lastVersionName).commit();
    }

}
