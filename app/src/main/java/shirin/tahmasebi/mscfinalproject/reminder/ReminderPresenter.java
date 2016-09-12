package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.io.models.Reminder;

public class ReminderPresenter implements ReminderInteractor.ReminderListener{

    private ReminderInteractor mInteractor;
    private ReminderView mView;

    public ReminderPresenter(ReminderView view) {
        mInteractor = new ReminderInteractor(this);
        mView = view;
    }

    public void createReminderClicked(Context context) {
        mInteractor.createNotification(context);
    }

    public void getRemindersList(Context context) {
        mInteractor.retrieveReminderList(context);

    }

    @Override
    public void onReminderListRetrieved(List<Reminder> list) {
        if (list.size() != 0) {
            mView.showHistoryList(list);
        }
    }

    public interface ReminderView {

        void showHistoryList(List<Reminder> list);
    }
}
