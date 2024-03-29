package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.Date;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;

public class ReminderAddInteractor {

    private ReminderAddListener mListener;

    public ReminderAddInteractor(ReminderAddListener listener) {
        mListener = listener;
    }

    public void retrieveOrganizationsList(Context context) {
        List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getOrganizationDao().loadAll();
        mListener.onOrganizationListRetrieved(list);
    }

    public void saveReminder(Date selectedDate, String customText, String organizationName,
                             Context context) {
        Reminder reminder = new Reminder();
        reminder.setDate(selectedDate);
        reminder.setText(customText);
        reminder.setOrganizationName(organizationName);

        ((BaseApplication) context.getApplicationContext())
                .daoSession.getReminderDao().insert(reminder);
        mListener.onSaveFinished(context, reminder);
    }

    public interface ReminderAddListener {

        void onOrganizationListRetrieved(List<Organization> list);

        void onSaveFinished(Context context, Reminder reminder);
    }
}

