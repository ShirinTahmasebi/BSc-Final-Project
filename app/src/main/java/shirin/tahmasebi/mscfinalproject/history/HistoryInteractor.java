package shirin.tahmasebi.mscfinalproject.history;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.History;

public class HistoryInteractor {

    HistoryListener mListener;

    public HistoryInteractor(HistoryListener listener) {
        mListener = listener;
    }

    public void retrieveHistoryList(Context context) {
        List<History> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getHistoryDao().loadAll();
        mListener.onHistoryListRetrieved(list);
    }

    public interface HistoryListener {

        void onHistoryListRetrieved(List<History> list);
    }
}
