package shirin.tahmasebi.mscfinalproject.organization;

import android.app.Activity;
import android.content.Context;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;
import shirin.tahmasebi.mscfinalproject.util.mail.gmail.MailPresenter;

class WriteEmailPresenter extends MailPresenter
        implements WriteEmailInteractor.WriteEmailListener {

    private WriteEmailView mView;
    private WriteEmailInteractor mInteractor;

    WriteEmailPresenter(WriteEmailView view, Activity context) {
        super((WriteEmailView) context, context);
        mView = view;
        mInteractor = new WriteEmailInteractor(this);
    }

    public void onStart(Context context, String type) {
        if (type.equals(WriteOptionEnum.EMAIL.getStringValue())) {
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
            }
        }
        mView.init(type);
    }


    void sendEmailOrSMS(Context context, String subject, String text, String type) {
        boolean validatedMailData = true;
        if (type.equals(WriteOptionEnum.EMAIL.getStringValue())) {
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
        } else if (type.equals(WriteOptionEnum.SMS.getStringValue())) {
            if ("".equals(text)) {
                mView.showInputEmailTextError();
                validatedMailData = false;
            } else {
                mView.clearInputEmailTextError();
            }
        }
        if (validatedMailData) {
            if (type.equals(WriteOptionEnum.EMAIL.getStringValue())) {
                if (!Helper.isNetworkAvailable(context)) {
                    mView.showNetworkProblemMessage();
                }
            }
            mInteractor.retrieveOrganization(
                    Long.parseLong(
                            ((Activity) context).getIntent().getStringExtra("ORGANIZATION_ID")),
                    context,
                    subject,
                    text,
                    type
            );
        }
    }


    @Override
    public void onRetrieveOrganizationFinished(Organization org, String subject,
                                               String text, String type) {
        if (WriteOptionEnum.EMAIL.getStringValue().equals(type)) {
            if (isGoogleType()) {
                super.sendEmail(subject, text, org.getEmailAddress());
            } else {
                mView.openSendingMailIntent(org,
                        subject,
                        text
                );
            }
        } else if (WriteOptionEnum.SMS.getStringValue().equals(type)) {
            mView.openSendingSMSIntent(org, text);
        }

    }

    void onEmailSent(Context context, Organization org, String text, String type) {
        mInteractor.saveSentMail(context, org, text, type);
    }

    interface WriteEmailView extends MailPresenter.MailView {
        void init(String type);

        void showInputEmailSubjectError();

        void clearInputEmailSubjectError();

        void showInputEmailTextError();

        void clearInputEmailTextError();

        void openSendingMailIntent(Organization org, String subject, String text);

        void showCompleteProfileDialog();

        void showChooseAccountDialog();

        void showNetworkProblemMessage();

        void openSendingSMSIntent(Organization org, String text);
    }
}
