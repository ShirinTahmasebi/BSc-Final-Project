package shirin.tahmasebi.mscfinalproject.reminder;

import android.os.Bundle;
import android.view.View;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class ReminderActivity extends MainActivity implements ReminderPresenter.ReminderView {

    private ReminderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ReminderPresenter(this);

        findViewById(R.id.reminder_createNewReminder_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.createReminderClicked(ReminderActivity.this);
                    }
                }
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reminder_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_reminder;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_reminder;
    }
}
