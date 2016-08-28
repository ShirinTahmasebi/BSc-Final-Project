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
        organization1.setPhoneNumber("02164411");
        organization1.setSiteUrl("http://www.leader.ir/fa/contact");

        organization2.setId(null);
        organization2.setName(getResources().getString(R.string.organization_name_two));
        organization2.setDescription(getResources().getString(R.string.organization_description_two));
        organization2.setWebsite(getResources().getString(R.string.organization_website_two));
        organization2.setImage(getResources().getString(R.string.organization_logo_two));
        organization2.setIsFavorite(false);
        organization2.setPhoneNumber("02188114150");
        organization2.setSiteUrl("https://www.ict.gov.ir/fa/companies/offices/complaints/" +
                "shekayat-%D8%B3%D8%A7%D9%85%D8%A7%D9%86%D9%87-%D8%B1%D8%B3%DB%8C%D8%AF%DA%AF%D" +
                "B%8C-%D8%B4%DA%A9%D8%A7%DB%8C%D8%AA");

        organization3.setId(null);
        organization3.setName(getResources().getString(R.string.organization_name_three));
        organization3.setDescription(getResources().getString(R.string.organization_description_three));
        organization3.setWebsite(getResources().getString(R.string.organization_website_three));
        organization3.setImage(getResources().getString(R.string.organization_logo_three));
        organization3.setIsFavorite(false);
        organization3.setEmailAddress("info@mfa.gov.ir");
        organization3.setSiteUrl("http://comment.mfa.gov.ir/index.aspx?fkeyid=&" +
                "siteid=453&pageid=22691");
        organization3.setPhoneNumber("02161156115");

        daoSession.getOrganizationDao().insert(organization1);
        daoSession.getOrganizationDao().insert(organization2);
        daoSession.getOrganizationDao().insert(organization3);

//        History history1 = new History();
//        history1.setOrganizationName(organization1.getName());
//        history1.setDate(ShamsiConverter.getCurrentShamsidate());
//        history1.setType(WriteOptionEnum.CALL.getIntValue());
//
//        History history2 = new History();
//        history2.setOrganizationName(organization1.getName());
//        history2.setDate(ShamsiConverter.getCurrentShamsidate());
//        history2.setType(WriteOptionEnum.CALL.getIntValue());
//
//        History history3 = new History();
//        history3.setOrganizationName(organization2.getName());
//        history3.setDate(ShamsiConverter.getCurrentShamsidate());
//        history3.setType(WriteOptionEnum.EMAIL.getIntValue());
//        history3.setEmailText("dfgddfjkrgmfdlkjfmndasjkfdslfanmdsljfmdslfjdsnkfjafnakjfndsf");
//
//        daoSession.getHistoryDao().insert(history1);
//        daoSession.getHistoryDao().insert(history2);
//        daoSession.getHistoryDao().insert(history3);

    }
}