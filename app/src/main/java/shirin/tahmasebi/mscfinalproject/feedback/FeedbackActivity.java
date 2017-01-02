package shirin.tahmasebi.mscfinalproject.feedback;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.profile.ProfileActivity;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class FeedbackActivity extends shirin.tahmasebi.mscfinalproject.MainActivity
        implements FeedbackPresenter.FeedbackView {

    FeedbackPresenter mPresenter;

    private final static String DEVELOPER_EMAIL = "tahmasebi_shirin@yahoo.com";

    @BindView(R.id.writeEmail_emailSubject_attractiveTextInputLayout)
    TextInputLayout mSubjectTextInputLayout;

    @BindView(R.id.writeEmail_emailText_attractiveTextInputLayout)
    TextInputLayout mMailTextTextInputLayout;


    @Override
    protected String getScreenName() {
        return FeedbackActivity.class.getSimpleName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FeedbackPresenter(this, this);
        mPresenter.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_feedback;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_feedback;
    }

    @Override
    public void init() {
        findViewById(R.id.writeEmail_cancelEmail_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
        findViewById(R.id.writeEmail_sendEmail_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String subject =
                                ((EditText) findViewById(R.id.writeEmail_emailSubject_editText))
                                        .getText().toString().trim();
                        String text =
                                ((EditText) findViewById(R.id.writeEmail_emailText_editText))
                                        .getText().toString().trim();
                        mPresenter.sendEmail(
                                subject,
                                text,
                                DEVELOPER_EMAIL,
                                FeedbackActivity.this);
                    }
                }
        );
    }

    @Override
    public void showInputEmailSubjectError() {
        mSubjectTextInputLayout.setErrorEnabled(true);
        mSubjectTextInputLayout.setError(getString(R.string.error_writeEmail_emptyEmailSubject));
    }

    @Override
    public void clearInputEmailSubjectError() {
        mSubjectTextInputLayout.setErrorEnabled(false);
        mSubjectTextInputLayout.setError(null);
    }

    @Override
    public void showInputEmailTextError() {
        mMailTextTextInputLayout.setErrorEnabled(true);
        mMailTextTextInputLayout.setError(getString(R.string.error_writeEmail_emptyEmailText));
    }

    @Override
    public void clearInputEmailTextError() {
        mMailTextTextInputLayout.setErrorEnabled(false);
        mMailTextTextInputLayout.setError(null);
    }

    @Override
    public void showEmailSendingResult(int messageID) {
        Helper.showToast(this, messageID);
    }

    @Override
    public void closeActivity(int messageID) {
        Helper.showToast(this, messageID);
        finish();
    }

    @Override
    public void showNetworkProblemMessage() {
        Helper.showToast(this, R.string.error_connection);
    }
}
