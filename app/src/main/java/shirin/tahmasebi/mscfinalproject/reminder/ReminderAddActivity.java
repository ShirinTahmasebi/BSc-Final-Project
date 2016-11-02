package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.organization.SpinnerAdapter;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class ReminderAddActivity extends MainActivity implements
        ReminderAddPresenter.ReminderAddView,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private ReminderAddPresenter mPresenter;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReminderAddPresenter(this);
        mPresenter.onStart(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reminderadd_layout;
    }

    @Override
    protected String getScreenName() {
        return ReminderAddActivity.class.getSimpleName();
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_reminderAdd;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_reminderAdd;
    }

    @Override
    public void init(List<String> organizationName) {
        initSpinner(organizationName);
        initTimePicker();
        initDatePicker();
        intiButtons();
    }

    @Override
    public void showError(int stringId) {
        Helper.showToast(this, stringId);
    }

    @Override
    public void closeCreateReminderActivity() {
        finish();
    }

    private void intiButtons() {
        findViewById(R.id.reminder_createReminder_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.createReminderClicked(
                                ((TextView) findViewById(R.id.reminder_timeHour_textView))
                                        .getText().toString(),
                                ((TextView) findViewById(R.id.reminder_timeMin_textView))
                                        .getText().toString(),
                                ((TextView) findViewById(R.id.reminder_dateYear_textView))
                                        .getText().toString(),
                                (Integer.parseInt(
                                        ((TextView) findViewById(R.id.reminder_dateMonth_textView))
                                                .getText().toString())) + "",
                                ((TextView) findViewById(R.id.reminder_dateDay_textView))
                                        .getText().toString(),
                                ((EditText) findViewById(R.id.reminder_customText_editText))
                                        .getText().toString(),
                                ((Spinner) findViewById(R.id.reminder_organizationName_spinner))
                                        .getSelectedItem().toString(),
                                ReminderAddActivity.this
                        );
                    }
                }
        );
    }

    private void initDatePicker() {
        PersianCalendar now = new PersianCalendar();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        findViewById(R.id.reminder_date_linearLayout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dpd.show(getFragmentManager(), DATEPICKER);
                    }
                });
    }

    private void initTimePicker() {
        PersianCalendar now = new PersianCalendar();
        final TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(PersianCalendar.HOUR_OF_DAY),
                now.get(PersianCalendar.MINUTE),
                true
        );
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(TIMEPICKER, "Dialog was cancelled");
            }
        });
        findViewById(R.id.reminder_time_linearLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tpd.show(getFragmentManager(), TIMEPICKER);
                    }
                }
        );
    }

    private void initSpinner(final List<String> list) {
        Spinner spinner = (Spinner) findViewById(R.id.reminder_organizationName_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(
                this,
                R.layout.item_spinner,
                list
        );
        spinner.setAdapter(adapter);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        ((FontableTextView) findViewById(R.id.reminder_timeHour_textView)).setText(
                Helper.convertToPersianDigits(hourString));
        ((FontableTextView) findViewById(R.id.reminder_timeMin_textView)).setText(
                Helper.convertToPersianDigits(minuteString));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        ((FontableTextView) findViewById(R.id.reminder_dateDay_textView)).setText(
                Helper.convertToPersianDigits(dayOfMonth + "")
        );
        ((FontableTextView) findViewById(R.id.reminder_dateMonth_textView)).setText(
                Helper.convertToPersianDigits((monthOfYear + 1) + "")
        );
        ((FontableTextView) findViewById(R.id.reminder_dateYear_textView)).setText(
                Helper.convertToPersianDigits(year + "")
        );
    }
}

