package shirin.tahmasebi.mscfinalproject.history;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.io.models.History;

public class HistoryPresenter implements HistoryInteractor.HistoryListener {

    HistoryView mView;
    HistoryInteractor mInteractor;

    public HistoryPresenter(HistoryView view) {
        mView = view;
        mInteractor = new HistoryInteractor(this);
    }

    public void getHistorysList(Context context) {
        mInteractor.retrieveHistoryList(context);
    }

    @Override
    public void onHistoryListRetrieved(List<History> list) {
        mView.showHistoryList(list);
    }

    public interface HistoryView {

        void showHistoryList(List<History> list);
    }
}
