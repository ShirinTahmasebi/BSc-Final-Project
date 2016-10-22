package shirin.tahmasebi.mscfinalproject.feedback;

import android.app.Activity;
import android.content.Context;

import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.mail.gmail.MailPresenter;

public class FeedbackPresenter extends MailPresenter
        implements FeedbackInteractor.FeedbackListener {
    FeedbackView mView;


    public FeedbackPresenter(FeedbackView view, Activity activity) {
        super(view, activity);
        mView = view;
    }

    public void onStart(Context context) {
        AuthPreferences authPreferences = new AuthPreferences(context);
        if (authPreferences.getKeyAccountType() == null ||
                authPreferences.getKeyAccountType().equals(
                        AccountTypeEnum.NothingSelected.toString())) {
            mView.showCompleteProfileDialog();
        } else if (((authPreferences.getKeyAccountType().equals(
                AccountTypeEnum.Google.toString()))
                && (authPreferences.getUser() == null))
                ) {
            mView.showChooseAccountDialog();
        } else {
            mView.init();
        }
    }

    public void sendEmail(String subject, String text, String developerEmail, Context context) {
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
                if (isGoogleType()) {
                    super.sendEmail(subject, text, developerEmail);
                } else {
                    mView.openSendingMailIntent(
                            subject,
                            text
                    );
                }
            }
        }
    }

    public interface FeedbackView extends MailView {
        void init();

        void showInputEmailSubjectError();

        void clearInputEmailSubjectError();

        void showInputEmailTextError();

        void clearInputEmailTextError();

        void openSendingMailIntent(String subject, String text);

        void showCompleteProfileDialog();

        void showChooseAccountDialog();

        void showNetworkProblemMessage();
    }
}

