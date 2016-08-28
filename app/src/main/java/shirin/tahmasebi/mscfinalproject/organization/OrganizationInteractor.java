package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;

public class OrganizationInteractor {
    private OrganizationListener mListener;

    public OrganizationInteractor(OrganizationListener listener) {
        mListener = listener;
    }

    public void retrieveOrganizationsList(Context context) {
        List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getOrganizationDao().loadAll();
        mListener.onOrganizationListRetrieved(list);
    }

    public void retrieveOrganizationsListByFavoriteProperty(Context context, Boolean isFavorited) {
        List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession
                        .getOrganizationDao()
                        .queryBuilder()
                        .where(OrganizationDao.Properties.IsFavorite.eq(isFavorited))
                        .list();
        mListener.onOrganizationListRetrieved(list);
    }

    public interface OrganizationListener {
        void onOrganizationListRetrieved(List<Organization> list);
    }
}

