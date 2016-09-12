package shirin.tahmasebi.mscfinalproject.reminder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class ReminderActivity extends MainActivity implements ReminderPresenter.ReminderView {

    private ReminderPresenter mPresenter;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ReminderPresenter(this);
        list.add("string 1");
        list.add("string 2");
        list.add("string 3");
        list.add("string 4");
        list.add("string 5");
        list.add("string 6");
        list.add("string 7");
        list.add("string 8");
        list.add("string 9");
        list.add("string 10");
        list.add("string 11");
        list.add("string 12");
        list.add("string 13");
        list.add("string 14");
        list.add("string 15");
        list.add("string 16");
        list.add("string 17");
        list.add("string 18");
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.reminder_reminderList_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ReminderAdapter(list));
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
