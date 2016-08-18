package shirin.tahmasebi.mscfinalproject;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import shirin.tahmasebi.mscfinalproject.io.models.DaoMaster;
import shirin.tahmasebi.mscfinalproject.io.models.DaoSession;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class BaseApplication extends Application {
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initialDaoSession();
        initialOrganizatoinDatabase();

    }

    private void initialDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "project-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private void initialOrganizatoinDatabase() {
        // TODO: In khat bayad pak beshe
        daoSession.getOrganizationDao().deleteAll();
        Organization organization1 = new Organization();
        Organization organization2 = new Organization();
        Organization organization3 = new Organization();

        organization1.setId(null);
        organization1.setName(
                getResources().getString(R.string.organization_name_one)
        );
        organization1.setDescription(
                getResources().getString(R.string.organization_description_one)
        );
        organization1.setWebsite(
                getResources().getString(R.string.organization_website_one)
        );
        organization1.setImage(
                getResources().getString(R.string.organization_logo_one)
        );
        organization1.setIsFavorite(false);

        organization2.setId(null);
        organization2.setName(getResources().getString(R.string.organization_name_two));
        organization2.setDescription(getResources().getString(R.string.organization_description_two));
        organization2.setWebsite(getResources().getString(R.string.organization_website_two));
        organization2.setImage(getResources().getString(R.string.organization_logo_two));
        organization2.setIsFavorite(false);

        organization3.setId(null);
        organization3.setName(getResources().getString(R.string.organization_name_three));
        organization3.setDescription(getResources().getString(R.string.organization_description_three));
        organization3.setWebsite(getResources().getString(R.string.organization_website_three));
        organization3.setImage(getResources().getString(R.string.organization_logo_three));
        organization3.setIsFavorite(false);

        daoSession.getOrganizationDao().insert(organization1);
        daoSession.getOrganizationDao().insert(organization2);
        daoSession.getOrganizationDao().insert(organization3);
    }
}