package app.com.baoviet.datasource;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.content.Context;

import app.com.baoviet.constant.Constant;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final String PREF_PASSWORD = "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUser(Context ctx, String userName, String passWord) {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_PASSWORD, passWord);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, Constant.EMPTY);
    }

    public static String getPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_PASSWORD, Constant.EMPTY);
    }
}
