package shirin.tahmasebi.mscfinalproject;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import de.greenrobot.dao.query.QueryBuilder;
import shirin.tahmasebi.mscfinalproject.io.models.DaoMaster;
import shirin.tahmasebi.mscfinalproject.io.models.DaoSession;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFav;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;
import shirin.tahmasebi.mscfinalproject.util.AnalyticsTrackers;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.SharedData;

import com.backendless.Backendless;

public class BaseApplication extends Application {
    public DaoSession daoSession;
    public static final String TAG = BaseApplication.class.getSimpleName();
    private static final String SECRET_KEY = "678D7606-F274-5759-FF60-6F089C9CA200";
    private static final String APPLICATION_ID = "22E74F57-FD44-EE76-FF1D-0AEB59AF8C00";
    private static final String BACKENDLESS_VERSION = "v1";
    public static BaseApplication mInstance;

    @Override
    public void onCreate() {
        mInstance = this;
        // ماژول SharedData را تعریف کن
        SharedData.init(PreferenceManager.getDefaultSharedPreferences(this));

        super.onCreate();

        SharedData.getInstance().put("locale", "fa");

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY, BACKENDLESS_VERSION);

        initialDaoSession();
        initialOrganizationDatabase();

        Bundle params = new Bundle();
        params.putString("image_name", "testName");
        params.putString("full_text", "testText");

        initGoogleAnalytics();
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

    }

    private void initGoogleAnalytics() {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

        // True to prevent sending reports to server (for debugging)
        analytics.setDryRun(false);
        analytics.setLocalDispatchPeriod(10); // In seconds, default 1800
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
        // Enable automatic activity tracking
        analytics.enableAutoActivityReports(this);
    }

    private void initialDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "project-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private void initialOrganizationDatabase() {
        String lastModifiedTime = SharedData.getInstance().getString("updated", "01/01/2000 00:00:00");
        String whereClause = "updated after '" + lastModifiedTime + "'";

        // Update last modified date.
        String currentDateAndTime = Helper.currentGregorianTimeDateFormat("MM/dd/yyyy hh:mm:ss");
        SharedData.getInstance().put("updated", currentDateAndTime);

        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setPageSize(100);

        Backendless.Persistence.of(Organization.class).find(dataQuery,
                new AsyncCallback<BackendlessCollection<Organization>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<Organization> foundOrganizations) {
                        for (Organization org : foundOrganizations.getCurrentPage()) {
                            if ((daoSession.getOrganizationDao().queryBuilder().where(
                                    OrganizationDao.Properties.No.eq(org.getNo()))).count() == 0) {
                                OrgFav orgFav = new OrgFav();
                                orgFav.setNo(org.getNo());
                                orgFav.setIsFavorite(false);
                                daoSession.getOrgFavDao().insert(orgFav);
                            } else {
                                QueryBuilder<Organization> existedOrgs =
                                        daoSession.getOrganizationDao().queryBuilder().where(
                                                OrganizationDao.Properties.No.eq(org.getNo()));
                                existedOrgs.buildDelete().executeDeleteWithoutDetachingEntities();
                            }
                            daoSession.getOrganizationDao().insert(org);
                        }
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Log.d("MSc final project", fault.getMessage());
                    }
                });
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

}