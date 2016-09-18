package shirin.tahmasebi.mscfinalproject.reminder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;

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
        mView.updateViewAfterReminderRemove();
    }

    public void removeReminderItem(Context mContext, Long id) {
        mInteractor.removeReminderItemById(mContext, id);
    }

    public void onStart() {
        mView.init();
    }

    public void onCreateReminderClicked() {
        mView.openAddReminderActivity();
    }

    public View selectLayoutToView(Context context) {
        if (mInteractor.isReminderListEmpty(context)) {
            return ((Activity) context).getLayoutInflater().inflate(
                    R.layout.activity_reminder_empty_layout,
                    null);
        } else {
            return ((Activity) context).getLayoutInflater().inflate(
                    R.layout.activity_reminder_layout,
                    null);
        }

    }

    public interface ReminderView {

        void showHistoryList(List<Reminder> list);

        void init();

        void openAddReminderActivity();

        void updateViewAfterReminderRemove();
    }
}
