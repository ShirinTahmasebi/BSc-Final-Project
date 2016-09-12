package shirin.tahmasebi.mscfinalproject;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import shirin.tahmasebi.mscfinalproject.io.models.DaoMaster;
import shirin.tahmasebi.mscfinalproject.io.models.DaoSession;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.ShamsiConverter;

public class BaseApplication extends Application {
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initialDaoSession();
        initialOrganizatoinDatabase();
        initialReminderDatabase();
    }

    private void initialReminderDatabase() {
        Reminder reminder1 = new Reminder();
        reminder1.setOrganizationName(getResources().getString(R.string.organization_name_one));
        reminder1.setDate(ShamsiConverter.getCurrentShamsidate());
        reminder1.setText("متن ریماندر اول");
        reminder1.setTime("12:12:12");
        Reminder reminder2 = new Reminder();
        reminder2.setOrganizationName(getResources().getString(R.string.organization_name_two));
        reminder2.setDate(ShamsiConverter.getCurrentShamsidate());
        reminder2.setText("متن ریماندر دوم");
        reminder2.setTime("12:12:12");
        Reminder reminder3 = new Reminder();
        reminder3.setOrganizationName(getResources().getString(R.string.organization_name_three));
        reminder3.setDate(ShamsiConverter.getCurrentShamsidate());
        reminder3.setText("متن ریماندر سوم");
        reminder3.setTime("12:12:12");
        Reminder reminder4 = new Reminder();
        reminder4.setOrganizationName(getResources().getString(R.string.organization_name_one));
        reminder4.setDate(ShamsiConverter.getCurrentShamsidate());
        reminder4.setText("متن ریماندر اول");
        reminder4.setTime("12:12:12");
        Reminder reminder5 = new Reminder();
        reminder5.setOrganizationName(getResources().getString(R.string.organization_name_two));
        reminder5.setDate(ShamsiConverter.getCurrentShamsidate());
        reminder5.setText("متن ریماندر دوم");
        reminder5.setTime("12:12:12");
        Reminder reminder6 = new Reminder();
        reminder6.setOrganizationName(getResources().getString(R.string.organization_name_three));
        reminder6.setDate(ShamsiConverter.getCurrentShamsidate());
        reminder6.setText("متن ریماندر سوم");
        reminder6.setTime("12:12:12");

        daoSession.getReminderDao().insertOrReplace(reminder1);
        daoSession.getReminderDao().insertOrReplace(reminder2);
        daoSession.getReminderDao().insertOrReplace(reminder3);
        daoSession.getReminderDao().insertOrReplace(reminder4);
        daoSession.getReminderDao().insertOrReplace(reminder5);
        daoSession.getReminderDao().insertOrReplace(reminder6);
    }

    private void initialDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "project-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private void initialOrganizatoinDatabase() {
        if (daoSession.getOrganizationDao()
                .queryBuilder()
                .where(
                        OrganizationDao.Properties.Name.eq(
                                getResources().getString(R.string.organization_name_one))
                ).list().size() == 0) {
            Organization organization1 = new Organization();

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
            daoSession.getOrganizationDao().insert(organization1);
        }

        if (daoSession.getOrganizationDao()
                .queryBuilder()
                .where(
                        OrganizationDao.Properties.Name.eq(
                                getResources().getString(R.string.organization_name_two))
                ).list().size() == 0) {

            Organization organization2 = new Organization();
            organization2.setName(getResources().getString(R.string.organization_name_two));
            organization2.setDescription(getResources().getString(R.string.organization_description_two));
            organization2.setWebsite(getResources().getString(R.string.organization_website_two));
            organization2.setImage(getResources().getString(R.string.organization_logo_two));
            organization2.setIsFavorite(false);
            organization2.setPhoneNumber("02188114150");
            organization2.setSiteUrl("https://www.ict.gov.ir/fa/companies/offices/complaints/" +
                    "shekayat-%D8%B3%D8%A7%D9%85%D8%A7%D9%86%D9%87-%D8%B1%D8%B3%DB%8C%D8%AF%DA%AF%D" +
                    "B%8C-%D8%B4%DA%A9%D8%A7%DB%8C%D8%AA");
            daoSession.getOrganizationDao().insert(organization2);
        }

        if (daoSession.getOrganizationDao()
                .queryBuilder()
                .where(OrganizationDao.Properties.Name.eq(
                        getResources().getString(R.string.organization_name_three)
                )).list().size() == 0) {
            Organization organization3 = new Organization();
            organization3.setName(getResources().getString(R.string.organization_name_three));
            organization3.setDescription(getResources().getString(R.string.organization_description_three));
            organization3.setWebsite(getResources().getString(R.string.organization_website_three));
            organization3.setImage(getResources().getString(R.string.organization_logo_three));
            organization3.setIsFavorite(false);
            organization3.setEmailAddress("info@mfa.gov.ir");
            organization3.setSiteUrl("http://comment.mfa.gov.ir/index.aspx?fkeyid=&" +
                    "siteid=453&pageid=22691");
            organization3.setPhoneNumber("02161156115");
            daoSession.getOrganizationDao().insert(organization3);
        }
    }
}