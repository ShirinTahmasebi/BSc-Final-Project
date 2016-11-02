package shirin.tahmasebi.mscfinalproject.dashboard;

import android.os.Bundle;
import android.view.View;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.feedback.FeedbackActivity;
import shirin.tahmasebi.mscfinalproject.history.HistoryActivity;
import shirin.tahmasebi.mscfinalproject.organization.OrganizationActivity;
import shirin.tahmasebi.mscfinalproject.profile.ProfileActivity;
import shirin.tahmasebi.mscfinalproject.reminder.ReminderActivity;
import shirin.tahmasebi.mscfinalproject.setting.SettingActivity;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class DashboardActivity extends MainActivity implements DashboardPresenter.DashboardView {

    @Override
    protected String getScreenName() {
        return DashboardActivity.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DashboardPresenter mPresenter = new DashboardPresenter(this);
        mPresenter.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_dashboard;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_dashboard;
    }

    @Override
    public void init() {
        findViewById(R.id.dashboard_historyItem_RelativeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.startActivity(DashboardActivity.this, HistoryActivity.class);
                    }
                }
        );
        findViewById(R.id.dashboard_companyPagesItem_RelativeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.startActivity(DashboardActivity.this, OrganizationActivity.class);
                    }
                }
        );
        findViewById(R.id.dashboard_profileItem_RelativeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.startActivity(DashboardActivity.this, ProfileActivity.class);
                    }
                }
        );
        findViewById(R.id.dashboard_reminderItem_RelativeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.startActivity(DashboardActivity.this, ReminderActivity.class);
                    }
                }
        );
        findViewById(R.id.dashboard_settingItem_RelativeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.startActivity(DashboardActivity.this, SettingActivity.class);
                    }
                }
        );
        findViewById(R.id.dashboard_feedbackItem_RelativeLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.startActivity(DashboardActivity.this, FeedbackActivity.class);
                    }
                }
        );
    }
}
