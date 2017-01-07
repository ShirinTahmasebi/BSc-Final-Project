package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;
import android.view.View;

import java.util.Date;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.Helper;

class ReminderAddViewPagerPresenter implements
        ReminderAddViewPagerInteractor.ReminderAddViewPagerListener {
    private final static int WEEK_IN_MILLISECONDS = 7 * 24 * 60 * 60 * 1000;
    private ReminderAddViewPagerInteractor mInteractor;
    private ReminderAddViewPagerView mView;
    private String organizationName = "";

    ReminderAddViewPagerPresenter(ReminderAddViewPagerView view) {
        mView = view;
        mInteractor = new ReminderAddViewPagerInteractor(this);
    }

    public void onStart(Context context) {
        mInteractor.retrieveOrganizationsList(context);
    }

    void viewPagerPageChanged(int position) {
        mView.updateIndicators(position);
    }

    void initWizard2(View view) {
        mView.initWizard2(view);
    }

    void createReminderClicked(String timeHour, String timeMin,
                               String dateYear, String dateMonth, String dateDay,
                               String customText, String organizationName,
                               Context context) {
        timeHour = Helper.convertToEnglishDigits(timeHour);
        timeMin = Helper.convertToEnglishDigits(timeMin);
        dateYear = Helper.convertToEnglishDigits(dateYear);
        dateMonth = Helper.convertToEnglishDigits(dateMonth);
        dateDay = Helper.convertToEnglishDigits(dateDay);
        if (organizationName == null || organizationName.equals("")) {
            mView.showError(R.string.error_reminder_organizationNotSelected);
            return;
        }
        if (context.getResources().getString(
                R.string.lable_reminder_noTimeSelected).equals(dateDay)) {
            mView.showError(R.string.error_reminder_dateNotSelected);
            return;
        }
        if (context.getResources().getString(
                R.string.lable_reminder_noTimeSelected).equals(timeHour)) {
            mView.showError(R.string.error_reminder_timeNotSelected);
            return;
        }


        Date currentDate = Helper.currentGregorianTimeDateFormat();
        Date selectedDate = Helper.convertSolarToGregorianTimeDateFormat(
                Integer.parseInt(timeHour),
                Integer.parseInt(timeMin),
                Integer.parseInt(dateYear),
                Integer.parseInt(dateMonth),
                Integer.parseInt(dateDay),
                (Integer.parseInt(timeHour) >= 12),
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

        mInteractor.saveReminder(
                selectedDate,
                customText,
                organizationName,
                context);
    }

    @Override
    public void onSaveFinished(Context context, Reminder reminder) {
        mView.closeCreateReminderActivity();
        Alarm.setAlarm(context, reminder);
    }

    @Override
    public void onOrganizationListRetrieved(List<Organization> list) {
        if (list == null) {
            return;
        }
        mView.initWizard1ListView(list);
    }

    void initWizard3(View view) {
        mView.initWizard3(view);
    }

    void initWizard1(View view) {
        mView.initWizard1(view);
    }


    void searchOrganization(Context context, String searchText, boolean closeKeyboard) {
        mInteractor.searchOrganizationByName(context, searchText);
        if (closeKeyboard) {
            mView.closeKeyboard();
        }
    }

    void setOrganizationName(CharSequence text) {
        organizationName = text.toString();
    }

    String getOrganizationName() {
        return organizationName;
    }

    interface ReminderAddViewPagerView {

        void updateIndicators(int position);

        void initWizard2(View view);

        void showError(int stringId);

        void closeCreateReminderActivity();

        void initWizard3(View view);

        void initWizard1(View view);

        void initWizard1ListView(List<Organization> organizations);

        void closeKeyboard();
    }
}
