package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class ReminderAddPresenter implements ReminderAddInteractor.ReminderAddListener {
    ReminderAddInteractor mInteractor;
    ReminderAddView mView;

    public ReminderAddPresenter(ReminderAddView view) {
        mView = view;
        mInteractor = new ReminderAddInteractor(this);
    }

    public void onStart(Context context) {
        mInteractor.retrieveOrganizationsList(context);
    }

    @Override
    public void onOrganizationListRetrieved(List<Organization> list) {
        if (list == null) {
            return;
        }
        List<String> organizationName = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            organizationName.add(list.get(i).getName());
        }
        mView.init(organizationName);
    }

    public interface ReminderAddView {

        void init(List<String> organizationName);
    }
}
