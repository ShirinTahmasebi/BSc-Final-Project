package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class ReminderAddPresenter implements ReminderAddInteractor.ReminderAddListener {
    ReminderAddInteractor mInteractor;
    ReminderAddView mView;
    private final static int WEEK_IN_MILLISECONDS = 7 * 24 * 60 * 60 * 1000;

    public ReminderAddPresenter(ReminderAddView view) {
        mView = view;
        mInteractor = new ReminderAddInteractor(this);
    }

    public void onStart(Context context) {
        mInteractor.retrieveOrganizationsList(context);
    }

    @Override
    public void onOrganizationListRetrieved(List<Organization> list) {
        if (list == null) {
            return;
        }
        List<String> organizationName = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            organizationName.add(list.get(i).getName());
        }
        mView.init(organizationName);
    }

    @Override
    public void onSaveFinished(Context context, Reminder reminder) {
        mView.closeCreateReminderActivity();
        SerializableReminder serializableReminder =
                new SerializableReminder(reminder.getId(),
                        reminder.getDate(),
                        reminder.getTime(),
                        reminder.getOrganizationName(),
                        reminder.getText());
        Alarm.setAlarm(context, reminder.getId().intValue(), serializableReminder);
    }

    public void createReminderClicked(String timeHour, String timeMin,
                                      String dateYear, String dateMonth, String dateDay,
                                      String customText, String organizationName,
                                      Context context) {
        timeHour = Helper.convertToEnglishDigits(timeHour);
        timeMin = Helper.convertToEnglishDigits(timeMin);
        dateYear = Helper.convertToEnglishDigits(dateYear);
        dateMonth = Helper.convertToEnglishDigits(dateMonth);
        dateDay = Helper.convertToEnglishDigits(dateDay);
        if (context.getResources().getString(
                R.string.lable_reminder_noTimeSelected).equals(timeHour)) {
            mView.showError(R.string.error_reminder_timeNotSelected);
            return;
        }
        if (context.getResources().getString(
                R.string.lable_reminder_noTimeSelected).equals(dateDay)) {
            mView.showError(R.string.error_reminder_dateNotSelected);
            return;
        }

        Date currentDate = Helper.currentGregorianTimeDateFormat();
        Date selectedDate = Helper.convertSolarToGregorianTimeDateFormat(
                Integer.parseInt(timeHour),
                Integer.parseInt(timeMin),
                Integer.parseInt(dateYear),
                Integer.parseInt(dateMonth),
                Integer.parseInt(dateDay),
                (Integer.parseInt(timeHour) > 12),
                false
        );


        if (currentDate == null || selectedDate == null) {
            mView.showError(R.string.error_reminder_errorWhileCreatingReminder);
            return;
        } else if (selectedDate.after(currentDate)) {
            if (selectedDate.getTime() - currentDate.getTime() >= WEEK_IN_MILLISECONDS) {
                mView.showError(R.string.error_reminder_timeMoreThanWeek);
                return;
            }
        } else {
            mView.showError(R.string.error_reminder_selectedTimeBeforeNow);
            return;
        }

        mInteractor.saveReminder(timeHour, timeMin,
                dateYear, dateMonth, dateDay,
                customText, organizationName,
                context);
    }

    public interface ReminderAddView {

        void init(List<String> organizationName);

        void showError(int stringId);

        void closeCreateReminderActivity();

    }
}
