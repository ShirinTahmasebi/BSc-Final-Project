package shirin.tahmasebi.mscfinalproject.feedback;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.mail.gmail.MailPresenter;

class FeedbackPresenter extends MailPresenter
        implements FeedbackInteractor.FeedbackListener {
    FeedbackView mView;
    FeedbackInteractor mInteractor;

    FeedbackPresenter(FeedbackView view, Activity activity) {
        super(view, activity);
        mView = view;
        mInteractor = new FeedbackInteractor(this);
    }

    public void onStart() {
        mView.init();
    }

    void sendEmail(String subject, String text, String developerEmail, Context context) {
        boolean validatedMailData = true;
        if ("".equals(subject)) {
            mView.showInputEmailSubjectError();
            validatedMailData = false;
        } else {
            mView.clearInputEmailSubjectError();
        }
        if ("".equals(text)) {
            mView.showInputEmailTextError();
            validatedMailData = false;
        } else {
            mView.clearInputEmailTextError();
        }

        if (validatedMailData) {
            if (!Helper.isNetworkAvailable(context)) {
                mView.showNetworkProblemMessage();
            } else {
                mInteractor.sendEmail(subject, text, developerEmail);
                mInteractor.sendFeedBackToServer(subject, text);
            }
        }
    }

    @Override
    public void onFeedBackSendingFinished(Boolean result) {
        if (result) {
            Log.w("mscFinalProject",
                    "پیام با موفقیت ارسال شد ");
            mView.showEmailSendingResult(R.string.toast_mail_success);
        } else {
            Log.w("mscFinalProject",
                    "ارسال پیام با شکست مواجه شد ");
            mView.showEmailSendingResult(R.string.toast_mail_error);
        }
    }

    interface FeedbackView extends MailView {
        void init();

        void showInputEmailSubjectError();

        void clearInputEmailSubjectError();

        void showInputEmailTextError();

        void clearInputEmailTextError();

        void showNetworkProblemMessage();
    }
}

