package shirin.tahmasebi.mscfinalproject.reminder;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.organization.SpinnerAdapter;

public class ReminderAddActivity extends MainActivity implements
        ReminderAddPresenter.ReminderAddView {

    private ReminderAddPresenter mPresenter;

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
    }

    private void initSpinner(final List<String> list) {
        Spinner spinner = (Spinner) findViewById(R.id.reminder_organizationName_spinner);
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
                        list.get(position) + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
