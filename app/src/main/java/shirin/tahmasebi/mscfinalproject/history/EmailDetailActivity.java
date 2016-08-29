package shirin.tahmasebi.mscfinalproject.history;

import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class EmailDetailActivity extends MainActivity
        implements EmailDetailPresenter.EmailDetailView {
    EmailDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EmailDetailPresenter(this);
        mPresenter.getEmailDetail(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emaildetails;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_emailDetail;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_emailDetails;
    }

    @Override
    public void showEmailDetail(String date, String text) {
        ((FontableTextView) findViewById(R.id.emailDetails_date_textView)).setText(
                Helper.convertToPersianDigits(date));

        ((FontableTextView) findViewById(R.id.emailDetails_mailText_textView)).setText(
                Helper.convertToPersianDigits(text));
    }
}
