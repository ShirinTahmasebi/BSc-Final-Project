package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFav;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFavDao;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.ShamsiConverter;
import shirin.tahmasebi.mscfinalproject.util.SharedData;
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

    public void refreshOrganizations(final Context context, final SwipeRefreshLayout swipeRefreshLayout) {

        String lastModifiedTime = SharedData.getInstance().getString("updated", "01/01/2000 00:00:00");
        String whereClause = "updated after '" + lastModifiedTime
                + "' or created after '" + lastModifiedTime + "'";

        // Update last modified date.
        String currentDateAndTime = Helper.currentGregorianTimeDateFormat("MM/dd/yyyy hh:mm:ss");
        SharedData.getInstance().put("updated", currentDateAndTime);


        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setPageSize(100);

        Backendless.Persistence.of(Organization.class).

                find(dataQuery,
                        new AsyncCallback<BackendlessCollection<Organization>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<Organization> foundOrganizations) {
                                for (Organization org : foundOrganizations.getCurrentPage()) {
                                    if ((((BaseApplication) context.getApplicationContext())
                                            .daoSession.getOrganizationDao()
                                            .queryBuilder()
                                            .where(
                                                    OrganizationDao.Properties.No.eq(
                                                            org.getNo()))).count() == 0
                                            ) {
                                        OrgFav orgFav = new OrgFav();
                                        orgFav.setNo(org.getNo());
                                        orgFav.setIsFavorite(false);
                                        ((BaseApplication) context.getApplicationContext())
                                                .daoSession.getOrgFavDao().insert(orgFav);
                                    } else {
                                        QueryBuilder<Organization> existedOrgs =
                                                ((BaseApplication) context.getApplicationContext())
                                                        .daoSession.getOrganizationDao()
                                                        .queryBuilder()
                                                        .where(
                                                                OrganizationDao.Properties.No.eq(
                                                                        org.getNo()
                                                                )
                                                        );
                                        existedOrgs.buildDelete().executeDeleteWithoutDetachingEntities();
                                    }
                                    ((BaseApplication) context.getApplicationContext())
                                            .daoSession.getOrganizationDao().insert(org);
                                }
                                mListener.onOrganizationListRefreshed(
                                        true,
                                        ((BaseApplication) context.getApplicationContext())
                                                .daoSession
                                                .getOrganizationDao()
                                                .loadAll()
                                        , swipeRefreshLayout);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                // An error has occurred
                                Log.e("MSc final project", fault.getMessage());
                                mListener.onOrganizationListRefreshed(
                                        false,
                                        ((BaseApplication) context.getApplicationContext())
                                                .daoSession
                                                .getOrganizationDao()
                                                .loadAll()
                                        , swipeRefreshLayout);
                            }
                        }

                );
    }

    interface OrganizationListener {
        void onOrganizationListRetrieved(List<Organization> list);

        void onRetrieveOrganizationFinished(Organization org, int retrieveReason);

        void onToggleFavoriteOrganizationFinished(int adapterPosition);

        void onOrganizationListRefreshed(boolean b, List<Organization> organizations, SwipeRefreshLayout swipeRefreshLayout);
    }
}

