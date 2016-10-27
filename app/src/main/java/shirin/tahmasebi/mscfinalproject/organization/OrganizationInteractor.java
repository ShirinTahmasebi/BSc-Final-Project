package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;
import shirin.tahmasebi.mscfinalproject.util.ShamsiConverter;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;

class OrganizationInteractor {
    private OrganizationListener mListener;

    OrganizationInteractor(OrganizationListener listener) {
        mListener = listener;
    }

    void retrieveOrganizationsList(Context context) {
        List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getOrganizationDao().loadAll();
        mListener.onOrganizationListRetrieved(list);
    }

    void retrieveOrganizationsListByFavoriteProperty(Context context, Boolean isFavorited) {
        List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession
                        .getOrganizationDao()
                        .queryBuilder()
                        .where(OrganizationDao.Properties.IsFavorite.eq(isFavorited))
                        .list();
        mListener.onOrganizationListRetrieved(list);
    }

    void searchOrganizationByName(Context context, String searchText) {
        List<Organization> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession
                        .getOrganizationDao()
                        .queryBuilder()
                        .where(OrganizationDao.Properties.Name.like("%" + searchText + "%"))
                        .list();
        mListener.onOrganizationListRetrieved(list);
    }

    void retrieveOrganization(long id, Context context, int retrieveReason) {
        Organization org = ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().load(id);
        mListener.onRetrieveOrganizationFinished(org, retrieveReason);
    }

    void toggleFavoriteOrganization(long id, Context context, int adapterPosition) {
        Organization org = ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().load(id);
        org.setIsFavorite(!org.getIsFavorite());
        ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrganizationDao().insertOrReplace(org);
        mListener.onToggleFavoriteOrganizationFinished(org, adapterPosition);
    }

    void saveDialedNumber(Context context, Organization org) {
        History history = new History();
        history.setType(WriteOptionEnum.CALL.getIntValue());
        history.setOrganizationName(org.getName());
        history.setDate(ShamsiConverter.getCurrentShamsiDate());
        ((BaseApplication) context.getApplicationContext()).daoSession
                .getHistoryDao().insert(history);
    }

    interface OrganizationListener {
        void onOrganizationListRetrieved(List<Organization> list);

        void onRetrieveOrganizationFinished(Organization org, int retrieveReason);

        void onToggleFavoriteOrganizationFinished(Organization org, int adapterPosition);
    }
}

