package shirin.tahmasebi.mscfinalproject.setting;

import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class SettingActivity extends MainActivity implements SettingPresenter.SettingView {

    private SettingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SettingPresenter(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_setting;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_setting;
    }
}
