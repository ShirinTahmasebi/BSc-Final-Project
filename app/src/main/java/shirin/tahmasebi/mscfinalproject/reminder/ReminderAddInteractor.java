package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class ReminderAddInteractor {

    private ReminderAddListener mListener;

    public ReminderAddInteractor(ReminderAddListener listener){
        mListener = listener;
    }

    public void retrieveOrganizationsList(Context context) {
      List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getOrganizationDao().loadAll();
        mListener.onOrganizationListRetrieved(list);
    }
    public interface ReminderAddListener{

        void onOrganizationListRetrieved(List<Organization> list);
    }
}

