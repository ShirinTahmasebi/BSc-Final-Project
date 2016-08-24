package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class OrganizationDetailInteractor {
    private OrganizationDetailListener mListener;

    public OrganizationDetailInteractor(OrganizationDetailListener listener) {
        mListener = listener;
    }

    public void retrieveOrganization(long id, Context context) {
        Organization org = ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().load(id);
        mListener.onRetrieveOrganizationFinished(org);
    }

    public void toggleFavoriteOrganization(long id, Context context) {
        Organization org = ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().load(id);
        org.setIsFavorite(!org.getIsFavorite());
        ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().insertOrReplace(org);
        mListener.onToggleFavoriteOrganizationFinished(org);
    }

    public interface OrganizationDetailListener {
        void onRetrieveOrganizationFinished(Organization org);

        void onToggleFavoriteOrganizationFinished(Organization org);
    }
}
