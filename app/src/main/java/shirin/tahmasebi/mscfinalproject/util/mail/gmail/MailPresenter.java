package shirin.tahmasebi.mscfinalproject.util.mail.gmail;

import android.app.Activity;
import android.util.Log;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;

public class MailPresenter implements MailInteractor.MailListener {

    private MailView mView;
    private Activity mActivity;
    private MailInteractor mInteractor;
    private AuthPreferences mAuthPreferences;

    public MailPresenter(MailView view, Activity context) {
        mView = view;
        mActivity = context;
        mInteractor = new MailInteractor(this, context);
        mAuthPreferences = new AuthPreferences(context);
    }

    @Override
    public void onEmailSendingFinished(Boolean result) {
        if (result) {
            Log.w("mscFinalProject",
                    "پیام با موفقیت ارسال شد " +
                            "  " +
                            mAuthPreferences.getUser() +
                            "  " +
                            mAuthPreferences.getToken());
            mView.showEmailSendingResult(R.string.toast_mail_success);
        } else {
            Log.w("mscFinalProject",
                    "ارسال پیام با شکست مواجه شد " +
                            "  " +
                            mAuthPreferences.getUser() +
                            "  " +
                            mAuthPreferences.getToken());
            mView.showEmailSendingResult(R.string.toast_mail_error);
        }
    }

    public void sendEmail(String subject, String body, String to) {
        mView.closeActivity(R.string.toast_mail_queueToSend);
        mInteractor.refreshTokenAndSendEmail(mActivity, subject, body, to);
    }

    public boolean isGoogleType() {
        return mAuthPreferences.getKeyAccountType().equals(AccountTypeEnum.Google.toString());
    }

    public interface MailView {
        void showEmailSendingResult(int messageID);

        void closeActivity(int messageID);
    }
}
