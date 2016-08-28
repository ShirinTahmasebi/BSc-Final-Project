package shirin.tahmasebi.mscfinalproject.history;

import android.content.Context;

public class EmailDetailPresenter implements EmailDetailInteractor.EmailDetailListener {
    EmailDetailInteractor mInteractor;
    EmailDetailView mView;

    public EmailDetailPresenter(EmailDetailView view) {
        mView = view;
        mInteractor = new EmailDetailInteractor(this);
    }

    public void getEmailDetail(Context context) {
        final String EXTRA_DATE = "emaildate";
        final String EXTRA_TEXT = "emailtext";
        String date = ((EmailDetailActivity) context).getIntent().getExtras().getString(EXTRA_DATE);
        String text = ((EmailDetailActivity) context).getIntent().getExtras().getString(EXTRA_TEXT);
        mView.showEmailDetail(date, text);
    }

    public interface EmailDetailView {

        void showEmailDetail(String date, String text);
    }
}
