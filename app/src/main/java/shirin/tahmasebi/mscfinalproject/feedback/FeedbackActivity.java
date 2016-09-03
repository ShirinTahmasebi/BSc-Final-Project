package shirin.tahmasebi.mscfinalproject.feedback;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.profile.ProfileActivity;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class FeedbackActivity extends shirin.tahmasebi.mscfinalproject.MainActivity implements FeedbackPresenter.FeedbackView {

    FeedbackPresenter mPresenter;

    @Bind(R.id.writeEmail_emailSubject_attractiveTextInputLayout)
    TextInputLayout mSubjectTextInputLayout;

    @Bind(R.id.writeEmail_emailText_attractiveTextInputLayout)
    TextInputLayout mMailTextTextInputLayout;
    private final static String DEVELOPER_EMAIL = "tahmasebi_shirin@yahoo.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FeedbackPresenter(this);
        mPresenter.onStart(this);
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
                                text);
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
    public void openSendingMailIntent(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:" + DEVELOPER_EMAIL));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{DEVELOPER_EMAIL});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(
                    this,
                    "برنامه‌ای برای ارسال ایمیل روی گوشی شما نصب نیست",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void showCompleteProfileDialog() {
        Helper.makeConfirmDialog(FeedbackActivity.this,
                getString(R.string.error_writeEmail_shouldCompleteProfile),
                ProfileActivity.class);
    }
}
