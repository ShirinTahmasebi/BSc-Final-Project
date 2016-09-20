package shirin.tahmasebi.mscfinalproject.reminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.util.Date;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.ShamsiConverter;

public class ReminderPresenter implements ReminderInteractor.ReminderListener {

    private ReminderInteractor mInteractor;
    private ReminderView mView;

    public ReminderPresenter(ReminderView view) {
        mInteractor = new ReminderInteractor(this);
        mView = view;
    }

    public void showNotification(Context context, SerializableReminder reminder) {
        mInteractor.createNotification(context, reminder);
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
    public void onReminderRemoved(Context context, Reminder reminder) {
        // برای اینکه چک کنه که برای اکتیویتی هست یا نه
        if (!context.isRestricted()) {
            mView.updateViewAfterReminderRemove();
        }
        Alarm.cancelAlarm(context, reminder.getId().intValue());
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

    @SuppressLint("InflateParams")
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

    public String timeToShow(Date date) {
        if (date != null) {
            return date.getHours() + ":" +
                    ((date.getMinutes() < 10) ? "0" : "")
                    + date.getMinutes();
        }
        return "";
    }

    public String dateToShow(Date date) {
        if (date != null) {
            return ShamsiConverter.getShamsiDate(date);
        }
        return "";
    }

    public interface ReminderView {

        void showHistoryList(List<Reminder> list);

        void init();

        void openAddReminderActivity();

        void updateViewAfterReminderRemove();
    }
}
