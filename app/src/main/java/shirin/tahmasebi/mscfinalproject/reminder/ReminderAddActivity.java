package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Spinner spinner;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReminderAddPresenter(this);
        mPresenter.onStart(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reminderadd_layout;
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
    }

    private void initDatePicker() {
        PersianCalendar now = new PersianCalendar();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        findViewById(R.id.reminder_date_textView).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.reminder_time_textView).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tpd.show(getFragmentManager(), TIMEPICKER);
                    }
                }
        );
    }

    private void initSpinner(final List<String> list) {
        spinner = (Spinner) findViewById(R.id.reminder_organizationName_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(
                this,
                R.layout.item_spinner,
                list
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int position, long id) {
                Toast.makeText(ReminderAddActivity.this,
                        spinner.getSelectedItem().toString() + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + ":" + minuteString;
        ((FontableTextView) findViewById(R.id.reminder_time_textView)).setText(
                Helper.convertToPersianDigits(time));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        ((FontableTextView) findViewById(R.id.reminder_date_textView)).setText(
                Helper.convertToPersianDigits(date));
    }
}
