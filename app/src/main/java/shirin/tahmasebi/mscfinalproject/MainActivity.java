package shirin.tahmasebi.mscfinalproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import shirin.tahmasebi.mscfinalproject.io.models.DaoMaster;
import shirin.tahmasebi.mscfinalproject.io.models.DaoSession;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public abstract class MainActivity extends Activity {
    public DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
        ButterKnife.bind(this);
        initialDaoSession();
        initialOrganizatoinDatabase();
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

        organization2.setId(null);
        organization2.setName("نام سازمان دوم");
        organization2.setDescription("توضیحات سازمان دوم");
        organization2.setWebsite("www.example2.com");

        organization3.setId(null);
        organization3.setName("نام سازمان سوم");
        organization3.setDescription("توضیحات سازمان سوم");
        organization3.setWebsite("www.example3.com");

        daoSession.getOrganizationDao().insert(organization1);
        daoSession.getOrganizationDao().insert(organization2);
        daoSession.getOrganizationDao().insert(organization3);
    }

    private void initialDaoSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "project-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    protected abstract int getLayoutId();

    private void setupToolbar() {
        TextView toolbarTitle;
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            toolbarTitle.setText(getActivityTitle());
        } else {
            // TODO:  این حالت معمولا زمانی پیش میاد که صفحه‌ای که باز شده اصلا تولبار دیفالت اکتیویتی رو نداره
        }
    }

    private String getActivityTitle() {
        if (getActivityTitleResourceId() == 0) {
            throw new IllegalStateException("Override getActivityTitleResourceId");
        } else {
            return getString(getActivityTitleResourceId());
        }
    }

    protected abstract int getActivityTitleResourceId();
}
