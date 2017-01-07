package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.WhereCondition;
import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFav;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFavDao;
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

    void retrieveOrganizationsListByFavoriteProperty(Context context, Boolean isFavored) {
        List<Organization> list;
        int favored = (isFavored) ? 1 : 0;
        list = ((BaseApplication) context.getApplicationContext())
                .daoSession
                .getOrganizationDao()
                .queryBuilder()
                .where(
                        new WhereCondition.StringCondition(
                                "T.\"NO\" IN (" +
                                        "SELECT \"NO\" " +
                                        "FROM \"ORG_FAV\" " +
                                        "WHERE  \"IS_FAVORITE\" = " +
                                        favored + ")"
                        )
                )
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

    void toggleFavoriteOrganization(long id, final Context context, final int adapterPosition) {
        final OrgFav org = ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrgFavDao().queryBuilder().where(
                        OrgFavDao.Properties.No.eq(id)
                ).list().get(0);
        ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrgFavDao().delete(org);
        org.setIsFavorite(!org.getIsFavorite());
        ((BaseApplication) context.getApplicationContext())
                .daoSession.getOrgFavDao().insert(org);
        mListener.onToggleFavoriteOrganizationFinished(adapterPosition);
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

        void onToggleFavoriteOrganizationFinished(int adapterPosition);
    }
}

