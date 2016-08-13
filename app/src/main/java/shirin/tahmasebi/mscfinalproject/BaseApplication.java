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
        organization1.setName("نام سازمان اول");
        organization1.setDescription("توضیحات سازمان اول");
        organization1.setWebsite("www.example.com");
        organization1.setImage("http://www.leader.ir/assets/images/logo.png");

        organization2.setId(null);
        organization2.setName("نام سازمان دوم");
        organization2.setDescription("توضیحات سازمان دوم");
        organization2.setWebsite("www.example2.com");
        organization2.setImage("http://parliran.ir/UploadedData/1/FavIcone/636037634735026912.png");

        organization3.setId(null);
        organization3.setName("نام سازمان سوم");
        organization3.setDescription("توضیحات سازمان سوم");
        organization3.setWebsite("www.example3.com");
        organization3.setImage("http://www.leader.ir/assets/images/logo1.png");

        daoSession.getOrganizationDao().insert(organization1);
        daoSession.getOrganizationDao().insert(organization2);
        daoSession.getOrganizationDao().insert(organization3);
    }

}
