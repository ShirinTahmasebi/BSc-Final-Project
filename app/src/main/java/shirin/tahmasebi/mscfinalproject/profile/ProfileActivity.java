package shirin.tahmasebi.mscfinalproject.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Arrays;

import shirin.tahmasebi.mscfinalproject.MainFragmentActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.organization.SpinnerAdapter;

public class ProfileActivity extends MainFragmentActivity implements ProfilePresenter.ProfileView {

    private ProfilePresenter mPresenter;
    private static final int AUTHORIZATION_CODE = 1993;
    private static final int ACCOUNT_CODE = 1601;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ProfilePresenter(this, this);
        mPresenter.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode != AUTHORIZATION_CODE) {
                if (requestCode == ACCOUNT_CODE) {
                    // کاربر اکانت گوگل را انتخاب کرده
                    // نام کاربری را بگیر و ذخیره کن
                    // توکن را بروز کن

                    mPresenter.googleAccountSelected(data);
                }
            }
        }
    }

    @Override
    public void init(int position) {
        initSpinner(position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_profile;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_profile;
    }

    private void initSpinner(int pos) {
        Spinner spinner = (Spinner) findViewById(R.id.profile_accountType_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(
                this,
                R.layout.item_profile_accounttype,
                Arrays.asList(getResources().getStringArray(
                        R.array.lable_profileAccountType
                ))
        );
        spinner.setAdapter(adapter);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int position, long id) {
                mPresenter.accountTypeSelected(ProfileActivity.this, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}


