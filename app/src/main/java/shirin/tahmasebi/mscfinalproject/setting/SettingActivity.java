package shirin.tahmasebi.mscfinalproject.setting;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.util.SharedData;

public class SettingActivity extends MainActivity implements SettingPresenter.SettingView {

    private SettingPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SettingPresenter(this);
        mPresenter.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected String getScreenName() {
        return SettingActivity.class.getSimpleName();
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_setting;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_setting;
    }

    @Override
    public void init() {
        initLanguage();
        initMap();
        initBrowser();
    }

    private void initBrowser() {
        if (SharedData.getInstance().getBoolean("defaultBrowser", true)) {
            ((Switch) findViewById(R.id.setting_useInAppBroweser_switch)).setChecked(true);
        } else {
            ((Switch) findViewById(R.id.setting_useInAppBroweser_switch)).setChecked(false);
        }
        ((Switch) findViewById(R.id.setting_useInAppBroweser_switch)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        mPresenter.useDefaultBrowserSwitchStateChanged(isChecked);
                    }
                }
        );
    }

    private void initMap() {
        if (SharedData.getInstance().getBoolean("defaultGps", true)) {
            ((Switch) findViewById(R.id.setting_useInAppGPS_switch)).setChecked(true);
        } else {
            ((Switch) findViewById(R.id.setting_useInAppGPS_switch)).setChecked(false);
        }
        ((Switch) findViewById(R.id.setting_useInAppGPS_switch)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        mPresenter.useDefaultGpsSwitchStateChanged(isChecked);
                    }
                }
        );
    }

    private void initLanguage() {
        if (SharedData.getInstance().getString("locale", "fa").equals("fa")) {
            ((Switch) findViewById(R.id.setting_selectLanguage_switch)).setChecked(true);
        } else {
            ((Switch) findViewById(R.id.setting_selectLanguage_switch)).setChecked(false);
        }
        ((Switch) findViewById(R.id.setting_selectLanguage_switch)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        mPresenter.languageSwitchStateChanged(SettingActivity.this, isChecked);
                        if (isChecked) {
                            changeLocale("fa");
                        } else {
                            changeLocale("en");
                        }
                    }
                }
        );
    }

    public void changeLocale(String locale) {
        setLanguage(locale);
        SharedData.getInstance().put("locale", locale);
    }
}
