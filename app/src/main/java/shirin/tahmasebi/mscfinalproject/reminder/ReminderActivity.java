package shirin.tahmasebi.mscfinalproject.reminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class ReminderActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reminder_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_reminder;
    }
}
