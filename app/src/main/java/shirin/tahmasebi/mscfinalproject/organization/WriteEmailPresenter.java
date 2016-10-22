package shirin.tahmasebi.mscfinalproject.organization;

import android.app.Activity;
import android.content.Context;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.mail.gmail.MailPresenter;

public class WriteEmailPresenter extends MailPresenter
        implements WriteEmailInteractor.WriteEmailListener {

    private WriteEmailView mView;
    private WriteEmailInteractor mInteractor;

    public WriteEmailPresenter(WriteEmailView view, Activity context) {
        super((WriteEmailView) context, context);
        mView = view;
        mInteractor = new WriteEmailInteractor(this);
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

    public void sendEmail(Context context, String subject, String text) {
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
                mInteractor.retrieveOrganization(
                        Long.parseLong(
                                ((Activity) context).getIntent().getStringExtra("ORGANIZATION_ID")),
                        context,
                        subject,
                        text
                );
            }
        }
    }

    @Override
    public void onRetrieveOrganizationFinished(Organization org, String subject, String text) {
        if (isGoogleType()) {
            super.sendEmail(subject, text, org.getEmailAddress());
        } else {
            mView.openSendingMailIntent(org,
                    subject,
                    text
            );
        }

    }

    public void onEmailSent(Context context, Organization org, String text) {
        mInteractor.saveSentMail(context, org, text);
    }

    public interface WriteEmailView extends MailPresenter.MailView {
        void init();

        void showInputEmailSubjectError();

        void clearInputEmailSubjectError();

        void showInputEmailTextError();

        void clearInputEmailTextError();

        void openSendingMailIntent(Organization org, String subject, String text);

        void showCompleteProfileDialog();

        void showChooseAccountDialog();

        void showNetworkProblemMessage();
    }
}
