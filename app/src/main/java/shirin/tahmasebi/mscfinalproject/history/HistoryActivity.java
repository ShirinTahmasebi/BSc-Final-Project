package shirin.tahmasebi.mscfinalproject.history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.History;


public class HistoryActivity extends MainActivity implements HistoryPresenter.HistoryView {
    HistoryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new HistoryPresenter(this);
        mPresenter.getHistorysList(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_history;
    }

    @Override
    public void showHistoryList(List<History> list) {
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.history_writingsList_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new HistoryAdapter(mPresenter, list));
    }
}
