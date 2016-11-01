package shirin.tahmasebi.mscfinalproject.setting;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

class SettingPresenter implements SettingInteractor.SettingListener {

    private SettingView mView;
    private SettingInteractor mInteractor;

    SettingPresenter(SettingView view) {
        mView = view;
        mInteractor = new SettingInteractor(this);
    }

    public void onStart() {
        mView.init();
    }

    void languageSwitchStateChanged(Context context, boolean isChecked) {
        Locale locale;
        if (isChecked) {
            locale = Locale.ENGLISH;
        } else {
            locale = new Locale("fa");
        }
        changeLocale(((Activity) context).getBaseContext(), locale);
    }

    private static void changeLocale(Context context, Locale locale) {
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(conf.locale);
        }

        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }

    void useDefaultGpsSwitchStateChanged(boolean isChecked) {
        mInteractor.changeGSPSwitch(isChecked);
    }

    void useDefaultBrowserSwitchStateChanged(boolean isChecked) {
        mInteractor.changeBrowserSwitch(isChecked);
    }

    interface SettingView {

        void init();
    }
}
