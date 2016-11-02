package shirin.tahmasebi.mscfinalproject;

import android.app.Application;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import shirin.tahmasebi.mscfinalproject.io.models.DaoMaster;
import shirin.tahmasebi.mscfinalproject.io.models.DaoSession;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;
import shirin.tahmasebi.mscfinalproject.util.AnalyticsTrackers;
import shirin.tahmasebi.mscfinalproject.util.SharedData;

public class BaseApplication extends MultiDexApplication {
    public DaoSession daoSession;
    public FirebaseAnalytics firebaseAnalytics;
    public static final String TAG = BaseApplication.class.getSimpleName();
    public static BaseApplication mInstance;

    @Override
    public void onCreate() {
        mInstance = this;
        // ماژول SharedData را تعریف کن
        SharedData.init(PreferenceManager.getDefaultSharedPreferences(this));
        super.onCreate();
        MultiDex.install(this);
        SharedData.getInstance().put("locale", "fa");
        initialDaoSession();
        initialOrganizationDatabase();
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle params = new Bundle();
        params.putString("image_name", "testName");
        params.putString("full_text", "testText");
        firebaseAnalytics.logEvent("share_image", params);
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
        TypedArray typedArray =
                getResources().obtainTypedArray(R.array.organizations);
        int count = typedArray.length();
        for (int counter = 0; counter < count; counter++) {
            String[] org = getResources().getStringArray(
                    typedArray.getResourceId(counter, 0)
            );

            if (daoSession.getOrganizationDao()
                    .queryBuilder()
                    .where(
                            OrganizationDao.Properties.Name.eq(org[0])
                    ).list().size() == 0) {
                Organization organization = new Organization();

                organization.setName(org[0]);
                organization.setTitle(org[0]);
                organization.setWebsite(org[1]);
                organization.setImage(org[2]);
                organization.setDescription(org[3]);
                organization.setIsFavorite(false);
                if ("NULL".equals(org[4])) {
                    organization.setSiteUrl(null);
                } else {
                    organization.setSiteUrl(org[4]);
                }
                if ("NULL".equals(org[5])) {
                    organization.setPhoneNumber(null);
                } else {
                    organization.setPhoneNumber(org[5]);
                }
                if ("NULL".equals(org[6])) {
                    organization.setSmsNumber(null);
                } else {
                    organization.setSmsNumber(org[6]);
                }
                if ("NULL".equals(org[7])) {
                    organization.setEmailAddress(null);
                } else {
                    organization.setEmailAddress(org[7]);
                }
                double lan = Double.parseDouble(org[8]);
                double lat = Double.parseDouble(org[9]);
                organization.setLan(lan);
                organization.setLat(lat);

                daoSession.getOrganizationDao().insert(organization);
            }
        }
        typedArray.recycle();
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