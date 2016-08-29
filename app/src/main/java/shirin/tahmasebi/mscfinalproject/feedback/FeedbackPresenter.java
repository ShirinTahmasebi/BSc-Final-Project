package shirin.tahmasebi.mscfinalproject.feedback;

public class FeedbackPresenter implements FeedbackInteractor.FeedbackListener {
    FeedbackInteractor mInteractor;
    FeedbackView mView;

    public FeedbackPresenter(FeedbackView view) {
        mView = view;
        mInteractor = new FeedbackInteractor(this);
    }

    public void onStart() {
        mView.init();
    }

    public void sendEmail(String subject, String text) {
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
            mView.openSendingMailIntent(
                    subject,
                    text
            );
        }
    }

    public interface FeedbackView {
        void init();

        void showInputEmailSubjectError();

        void clearInputEmailSubjectError();

        void showInputEmailTextError();

        void clearInputEmailTextError();

        void openSendingMailIntent(String subject, String text);
    }
}

