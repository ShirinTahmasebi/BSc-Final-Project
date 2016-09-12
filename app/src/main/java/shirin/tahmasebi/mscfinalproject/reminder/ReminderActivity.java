package shirin.tahmasebi.mscfinalproject.reminder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;

public class ReminderActivity extends MainActivity implements ReminderPresenter.ReminderView {

    private ReminderPresenter mPresenter;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ReminderPresenter(this);
        mPresenter.getRemindersList(this);
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

    @Override
    public void showHistoryList(List<Reminder> list) {
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.reminder_reminderList_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ReminderAdapter(list));
    }
}
