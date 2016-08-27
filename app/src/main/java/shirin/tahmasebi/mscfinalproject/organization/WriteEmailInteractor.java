package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class WriteEmailInteractor {
    private WriteEmailListener mListener;

    public WriteEmailInteractor(WriteEmailListener listener) {
        mListener = listener;
    }


    public void retrieveOrganization(long id, Context context, String subject, String text) {
        Organization org = ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().load(id);
        mListener.onRetrieveOrganizationFinished(org, subject, text);
    }

    public interface WriteEmailListener {
        void onRetrieveOrganizationFinished(Organization org, String subject, String text);
    }
}
