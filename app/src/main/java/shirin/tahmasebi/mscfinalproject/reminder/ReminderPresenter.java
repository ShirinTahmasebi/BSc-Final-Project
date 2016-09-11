package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

public class ReminderPresenter implements ReminderInteractor.ReminderListener{

    private ReminderInteractor mInteractor;
    private ReminderView mView;

    public ReminderPresenter(ReminderView view) {
        mInteractor = new ReminderInteractor(this);
        mView = view;
    }

    public void createReminderClicked(Context context) {
        mInteractor.createNotification(context);
    }

    public interface ReminderView {

    }
}
