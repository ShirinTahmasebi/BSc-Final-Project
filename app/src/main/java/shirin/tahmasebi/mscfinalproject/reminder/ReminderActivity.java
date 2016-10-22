package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class ReminderActivity extends MainActivity implements ReminderPresenter.ReminderView {

    private ReminderPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter = new ReminderPresenter(this);
        setContentView(mPresenter.selectLayoutToView(this));
        mPresenter.onStart();
        mPresenter.getRemindersList(this);
    }

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_reminder;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_reminder;
    }

    @Override
    public void showHistoryList(List<Reminder> list) {
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.reminder_reminderList_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ReminderAdapter(mPresenter, list, this));
    }

    @Override
    public void init() {
        findViewById(R.id.reminder_addReminder_FAB).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onCreateReminderClicked();
                    }
                }
        );
    }

    @Override
    public void openAddReminderActivity() {
        Intent intent = new Intent(this, ReminderAddActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateViewAfterReminderRemove() {

        Helper.showToast(this, R.string.lable_reminderItem_deletedSuccessful);

        mPresenter = new ReminderPresenter(this);
        setContentView(mPresenter.selectLayoutToView(this));
        mPresenter.onStart();
        mPresenter.getRemindersList(this);

    }
}
