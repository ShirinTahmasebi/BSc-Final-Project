package shirin.tahmasebi.mscfinalproject.organization;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class WriteEmailActivity extends MainActivity implements WriteEmailPresenter.WriteEmailView {

    WriteEmailPresenter mPresenter;

    @Bind(R.id.writeEmail_emailSubject_attractiveTextInputLayout)
    TextInputLayout mSubjectTextInputLayout;

    @Bind(R.id.writeEmail_emailText_attractiveTextInputLayout)
    TextInputLayout mMailTextTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new WriteEmailPresenter(this);
        mPresenter.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_email;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_writeEmail;
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
                                WriteEmailActivity.this,
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
    public void openSendingMailIntent(Organization org, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:" + org.getEmailAddress()));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{org.getEmailAddress()});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(intent);
            mPresenter.onEmailSent(this, org, subject, text);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(
                    this,
                    "برنامه‌ای برای ارسال ایمیل روی گوشی شما نصب نیست",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
