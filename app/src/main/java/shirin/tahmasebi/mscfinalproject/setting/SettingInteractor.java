package shirin.tahmasebi.mscfinalproject.setting;

import android.content.Context;

import shirin.tahmasebi.mscfinalproject.util.SharedData;

class SettingInteractor {

    private SettingListener mListener;

    SettingInteractor(SettingListener listener) {
        mListener = listener;
    }

    void changeGSPSwitch(boolean isChecked) {
        SharedData.getInstance().put("defaultGps", isChecked);
    }

    void changeBrowserSwitch(boolean isChecked) {
        SharedData.getInstance().put("defaultBrowser", isChecked);
    }

    interface SettingListener {

    }
}
