package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.ShamsiConverter;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;

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

    public void saveSentMail(Context context, Organization org, String text) {
        History history = new History();
        history.setType(WriteOptionEnum.EMAIL.getIntValue());
        history.setEmailText(text);
        history.setOrganizationName(org.getName());
        history.setDate(ShamsiConverter.getCurrentShamsiDate());
        ((BaseApplication) context.getApplicationContext()).daoSession
                .getHistoryDao().insert(history);
    }

    public interface WriteEmailListener {
        void onRetrieveOrganizationFinished(Organization org, String subject, String text);
    }
}
