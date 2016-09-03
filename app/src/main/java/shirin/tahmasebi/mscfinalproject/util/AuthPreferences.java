package shirin.tahmasebi.mscfinalproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AuthPreferences {

    private static final String KEY_USER = "userid";
    private static final String KEY_TOKEN = "mytoken";

    private SharedPreferences preferences;

    public AuthPreferences(Context context) {
        preferences = context.getSharedPreferences("authoris", Context.MODE_PRIVATE);
    }

    public void setUser(String user) {
        Editor editor = preferences.edit();
        editor.putString(KEY_USER, user);
        editor.apply();
    }

    public void setToken(String password) {
        Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN, password);
        editor.apply();
    }

    public String getUser() {
        return preferences.getString(KEY_USER, null);
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, null);
    }
}