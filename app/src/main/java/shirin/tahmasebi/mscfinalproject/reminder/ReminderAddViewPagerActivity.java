package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainFragmentActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.view.FontableButton;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class ReminderAddViewPagerActivity extends MainFragmentActivity implements
        ReminderAddViewPagerPresenter.ReminderAddViewPagerView,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    ReminderAddViewPagerPresenter mPresenter;
    private final static int WIZARD_PAGES_COUNT = 3;

    private ViewPager viewPager;
    private View indicator1;
    private View indicator2;
    private View indicator3;


    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";
    private EditText reminderEditText;
    private TextView reminderTimeHourTextView;
    private TextView reminderTimeMinTextView;
    private TextView reminderDateYearTextView;
    private TextView reminderDateMonthTextView;
    private TextView reminderDateDayTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReminderAddViewPagerPresenter(this);
        init();
    }

    private void init() {
        initViewPager();
        initNextPrevButtons();
        initIndicators();
        updateIndicators(0);
    }

    private void initIndicators() {

        indicator1 = findViewById(R.id.indicator1);
        indicator2 = findViewById(R.id.indicator2);
        indicator3 = findViewById(R.id.indicator3);
    }

    private void initNextPrevButtons() {
        FontableButton prev = (FontableButton) findViewById(R.id.prev);
        final FontableButton next = (FontableButton) findViewById(R.id.next);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                } else {
                    viewPager.setCurrentItem(WIZARD_PAGES_COUNT - 1);
                }
                if (viewPager.getCurrentItem() < WIZARD_PAGES_COUNT - 1) {
                    next.setText(R.string.next);
                } else {
                    next.setText(R.string.lable_reminder_createReminder);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < (WIZARD_PAGES_COUNT - 2)) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    next.setText(R.string.next);
                } else if (viewPager.getCurrentItem() == (WIZARD_PAGES_COUNT - 2)) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    next.setText(R.string.lable_reminder_createReminder);
                } else if (viewPager.getCurrentItem() == (WIZARD_PAGES_COUNT - 1)) {
                    mPresenter.createReminderClicked(
                            reminderTimeHourTextView.getText().toString(),
                            reminderTimeMinTextView.getText().toString(),
                            reminderDateYearTextView.getText().toString(),
                            reminderDateMonthTextView.getText().toString(),
                            reminderDateDayTextView.getText().toString(),
                            reminderEditText.getText().toString(),
                            mPresenter.getOrganizationName(),
                            ReminderAddViewPagerActivity.this
                    );
                }
            }
        });
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(
                new AddReminderViewPagerAdapter(
                        getSupportFragmentManager(),
                        mPresenter
                )
        );
        viewPager.setOnPageChangeListener(new AddReminderWizardPageChangeListener(mPresenter));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reminderadd_viewpager_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return 0;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_reminderAdd;
    }

    @Override
    public void updateIndicators(int position) {
        switch (position) {
            case 0:
                indicator1.setBackgroundResource(R.drawable.indicator_bg);
                indicator2.setBackgroundResource(R.drawable.bg_filter_stroke_rounded);
                indicator3.setBackgroundResource(R.drawable.bg_filter_stroke_rounded);
                break;

            case 1:
                indicator1.setBackgroundResource(R.drawable.bg_filter_stroke_rounded);
                indicator2.setBackgroundResource(R.drawable.indicator_bg);
                indicator3.setBackgroundResource(R.drawable.bg_filter_stroke_rounded);
                break;

            case 2:
                indicator1.setBackgroundResource(R.drawable.bg_filter_stroke_rounded);
                indicator2.setBackgroundResource(R.drawable.bg_filter_stroke_rounded);
                indicator3.setBackgroundResource(R.drawable.indicator_bg);
                break;
        }
    }

    @Override
    public void initWizard2(View view) {
        reminderTimeHourTextView = ((TextView) view.findViewById(R.id.reminder_timeHour_textView));
        reminderTimeMinTextView = ((TextView) view.findViewById(R.id.reminder_timeMin_textView));
        reminderDateYearTextView = ((TextView) view.findViewById(R.id.reminder_dateYear_textView));
        reminderDateMonthTextView = ((TextView) view.findViewById(R.id.reminder_dateMonth_textView));
        reminderDateDayTextView = ((TextView) view.findViewById(R.id.reminder_dateDay_textView));

        initTimePicker(view);
        initDatePicker(view);
    }


    private void initDatePicker(View view) {
        PersianCalendar now = new PersianCalendar();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
        view.findViewById(R.id.reminder_date_linearLayout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dpd.show(getFragmentManager(), DATEPICKER);
                    }
                });
    }

    private void initTimePicker(View view) {
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
        view.findViewById(R.id.reminder_time_linearLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tpd.show(getFragmentManager(), TIMEPICKER);
                    }
                }
        );
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

    @Override
    public void showError(int stringId) {
        Helper.showToast(this, stringId);
    }

    @Override
    public void closeCreateReminderActivity() {
        finish();
    }

    @Override
    public void initWizard3(View view) {
        reminderEditText = ((EditText) view.findViewById(R.id.reminder_customText_editText));
    }

    @Override
    public void initWizard1(View view) {
        recyclerView = (RecyclerView)
                view.findViewById(R.id.organization_organizationList_recyclerView);
        mPresenter.onStart(this);
        initSearchLayout(view);
    }

    private void initSearchLayout(final View view) {
        ((EditText) view.findViewById(R.id.organization_search_editText)).setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView,
                                                  int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String searchText =
                                    ((EditText) view.findViewById(R.id.organization_search_editText))
                                            .getText().toString();

                            mPresenter.searchOrganization(
                                    ReminderAddViewPagerActivity.this,
                                    searchText,
                                    true
                            );

                            return true;
                        }
                        return false;
                    }
                }
        );

        ((EditText) view.findViewById(R.id.organization_search_editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.searchOrganization(
                        ReminderAddViewPagerActivity.this,
                        s.toString(),
                        false
                );

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void closeKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void initWizard1ListView(List<Organization> organizations) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SelectOrganizationInReminderAdapter selectOrganizationInReminderAdapter =
                new SelectOrganizationInReminderAdapter(mPresenter, organizations, this);
        recyclerView.setAdapter(selectOrganizationInReminderAdapter);
    }
}
