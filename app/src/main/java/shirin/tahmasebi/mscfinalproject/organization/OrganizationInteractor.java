package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class OrganizationInteractor {
    private OrganizationListener mListener;

    public OrganizationInteractor(OrganizationListener listener) {
        mListener = listener;
    }

    public void retrieveOrganizationsList(Context context) {
        List<Organization> list =
                ((MainActivity) context).daoSession.getOrganizationDao().loadAll();
        mListener.onOrganizationListRetrieved(list);
    }

    public interface OrganizationListener {
        void onOrganizationListRetrieved(List<Organization> list);
    }
}

