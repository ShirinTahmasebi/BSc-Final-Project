package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.R;
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

    public void saveReminder(String timeHour, String timeMin,
                             String dateYear, String dateMonth, String dateDay,
                             String customText, String organizationName, Context context) {
        Reminder reminder = new Reminder();
        reminder.setTime(
                timeHour
                        + context.getString(R.string.lable_reminder_timeDivider)
                        + timeMin
        );
        reminder.setDate(
                dateYear
                        + context.getString(R.string.lable_reminder_dateDivider)
                        + dateMonth
                        + context.getString(R.string.lable_reminder_dateDivider)
                        + dateDay
        );

        reminder.setText(customText);
        reminder.setOrganizationName(organizationName);

        ((BaseApplication) context.getApplicationContext())
                .daoSession.getReminderDao().insert(reminder);
        mListener.onSaveFinished();
    }

    public interface ReminderAddListener {

        void onOrganizationListRetrieved(List<Organization> list);

        void onSaveFinished();
    }
}

