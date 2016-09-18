package shirin.tahmasebi.mscfinalproject;

import android.app.Application;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;

import shirin.tahmasebi.mscfinalproject.io.models.DaoMaster;
import shirin.tahmasebi.mscfinalproject.io.models.DaoSession;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;

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
                    organization.setEmailAddress(null);
                } else {
                    organization.setEmailAddress(org[6]);
                }
                daoSession.getOrganizationDao().insert(organization);
            }
        }
        typedArray.recycle();
    }
}