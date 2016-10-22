package shirin.tahmasebi.mscfinalproject.history;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.History;

public class HistoryPresenter implements HistoryInteractor.HistoryListener {

    HistoryView mView;
    HistoryInteractor mInteractor;

    public HistoryPresenter(HistoryView view) {
        mView = view;
        mInteractor = new HistoryInteractor(this);
    }

    public void getHistoryList(Context context) {
        mInteractor.retrieveHistoryList(context);
    }

    @Override
    public void onHistoryListRetrieved(List<History> list) {
        if (list.size() != 0) {
            mView.showHistoryList(list);
        }
    }

    public int selectLayoutToView(Context context) {
        if (!mInteractor.isHistoryListEmpty(context)) {
            return R.layout.activity_history_layout;
        } else {
            return R.layout.activity_history_empty_layout;
        }
    }


    public void openEmailDetail(String date, String emailText) {
        mView.showEmailDetailActivity(date, emailText);
    }

    public interface HistoryView {

        void showHistoryList(List<History> list);

        void showEmailDetailActivity(String date, String emailText);
    }
}
