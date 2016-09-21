package shirin.tahmasebi.mscfinalproject.util;

import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedData {
    private static SharedData INSTANCE;
    private SharedPreferences preferences;
    private HashMap<String, Object> nonPersistentData;

    public static void init(SharedPreferences sharedPrefs) {
        INSTANCE = new SharedData(sharedPrefs);
    }

    public static SharedData getInstance() {
        return INSTANCE;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    private SharedData(SharedPreferences sharedPrefsInstance) {
        preferences = sharedPrefsInstance;
        nonPersistentData = new HashMap<>();
    }

    public void put(String key, Object value) {
        put(key, value, true);
    }

    public void put(String key, Object value, boolean persistent) {
        if (persistent) {
            SharedPreferences.Editor editor = preferences.edit();
            if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else {
                throw new IllegalArgumentException("Argument is not serializable");
            }
            editor.commit();
        } else {
            nonPersistentData.put(key, value);
        }
    }

    public Object getOrPut(String key, Object defaultValue) {
        return getOrPut(key, defaultValue, true);
    }

    public Object getOrPut(String key, Object defaultValue, boolean persistent) {
        if (!contains(key)) {
            put(key, defaultValue, persistent);
        }
        return get(key, defaultValue);
    }

    public void removeNonPersistentData(String key) {
        if (nonPersistentData.containsKey(key)) {
            nonPersistentData.remove(key);
        }
    }

    public void remove(String key) {
        removeNonPersistentData(key);
        if (preferences.contains(key)) {
            preferences.edit().remove(key).commit();
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return (boolean) get(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return (Integer) get(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return (Long) get(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return (String) get(key, defaultValue);
    }

    public Object get(String key, Object defaultValue) {
        if (nonPersistentData.containsKey(key)) {
            return nonPersistentData.get(key);
        } else {
            if (defaultValue == null || defaultValue instanceof String)
                return preferences.getString(key, (String) defaultValue);
            else if (defaultValue instanceof Integer)
                return preferences.getInt(key, (Integer) defaultValue);
            else if (defaultValue instanceof Boolean)
                return preferences.getBoolean(key, (Boolean) defaultValue);
            else if (defaultValue instanceof Float)
                return preferences.getFloat(key, (Float) defaultValue);
            else if (defaultValue instanceof Long)
                return preferences.getLong(key, (Long) defaultValue);
        }
        return defaultValue;
    }

    public boolean contains(String key) {
        return preferences.contains(key) || nonPersistentData.containsKey(key);
    }
}
