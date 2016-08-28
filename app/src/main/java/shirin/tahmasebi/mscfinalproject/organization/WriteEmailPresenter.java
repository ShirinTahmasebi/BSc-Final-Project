package shirin.tahmasebi.mscfinalproject.organization;

import android.app.Activity;
import android.content.Context;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class WriteEmailPresenter implements WriteEmailInteractor.WriteEmailListener {

    private WriteEmailView mView;
    private WriteEmailInteractor mInteractor;

    public WriteEmailPresenter(WriteEmailView view) {
        mView = view;
        mInteractor = new WriteEmailInteractor(this);
    }

    public void onStart() {
        mView.init();
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
            mInteractor.retrieveOrganization(
                    Long.parseLong(((Activity) context).getIntent().getStringExtra("ORGANIZATION_ID")),
                    context,
                    subject,
                    text
            );
        }
    }

    @Override
    public void onRetrieveOrganizationFinished(Organization org, String subject, String text) {
        mView.openSendingMailIntent(org,
                subject,
                text
        );
    }

    public void onEmailSent(Context context, Organization org, String subject, String text) {
        mInteractor.saveSentMail(context, org, text);
    }

    public interface WriteEmailView {
        void init();

        void showInputEmailSubjectError();

        void clearInputEmailSubjectError();

        void showInputEmailTextError();

        void clearInputEmailTextError();

        void openSendingMailIntent(Organization org, String subject, String text);
    }
}
