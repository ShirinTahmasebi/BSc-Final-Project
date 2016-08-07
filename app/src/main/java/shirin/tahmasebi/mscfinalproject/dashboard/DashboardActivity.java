package shirin.tahmasebi.mscfinalproject.dashboard;

import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class DashboardActivity extends MainActivity implements DashboardPresenter.DashboardView {
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_dashboard;
    }
}
