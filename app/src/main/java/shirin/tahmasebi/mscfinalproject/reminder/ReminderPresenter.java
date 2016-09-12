package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class ReminderPresenter implements ReminderInteractor.ReminderListener {

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

    @Override
    public void onReminderRemoved(Context context) {
        Helper.showToast(context, R.string.lable_reminderItem_deletedSuccessful);
    }

    public void removeReminderItem(Context mContext, Long id) {
        mInteractor.removeReminderItemById(mContext, id);
    }

    public interface ReminderView {

        void showHistoryList(List<Reminder> list);
    }
}
