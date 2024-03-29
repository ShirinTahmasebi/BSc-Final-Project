package shirin.tahmasebi.mscfinalproject.io.models;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFav;

import shirin.tahmasebi.mscfinalproject.io.models.OrganizationDao;
import shirin.tahmasebi.mscfinalproject.io.models.HistoryDao;
import shirin.tahmasebi.mscfinalproject.io.models.ReminderDao;
import shirin.tahmasebi.mscfinalproject.io.models.OrgFavDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig organizationDaoConfig;
    private final DaoConfig historyDaoConfig;
    private final DaoConfig reminderDaoConfig;
    private final DaoConfig orgFavDaoConfig;

    private final OrganizationDao organizationDao;
    private final HistoryDao historyDao;
    private final ReminderDao reminderDao;
    private final OrgFavDao orgFavDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        organizationDaoConfig = daoConfigMap.get(OrganizationDao.class).clone();
        organizationDaoConfig.initIdentityScope(type);

        historyDaoConfig = daoConfigMap.get(HistoryDao.class).clone();
        historyDaoConfig.initIdentityScope(type);

        reminderDaoConfig = daoConfigMap.get(ReminderDao.class).clone();
        reminderDaoConfig.initIdentityScope(type);

        orgFavDaoConfig = daoConfigMap.get(OrgFavDao.class).clone();
        orgFavDaoConfig.initIdentityScope(type);

        organizationDao = new OrganizationDao(organizationDaoConfig, this);
        historyDao = new HistoryDao(historyDaoConfig, this);
        reminderDao = new ReminderDao(reminderDaoConfig, this);
        orgFavDao = new OrgFavDao(orgFavDaoConfig, this);

        registerDao(Organization.class, organizationDao);
        registerDao(History.class, historyDao);
        registerDao(Reminder.class, reminderDao);
        registerDao(OrgFav.class, orgFavDao);
    }
    
    public void clear() {
        organizationDaoConfig.getIdentityScope().clear();
        historyDaoConfig.getIdentityScope().clear();
        reminderDaoConfig.getIdentityScope().clear();
        orgFavDaoConfig.getIdentityScope().clear();
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public HistoryDao getHistoryDao() {
        return historyDao;
    }

    public ReminderDao getReminderDao() {
        return reminderDao;
    }

    public OrgFavDao getOrgFavDao() {
        return orgFavDao;
    }

}
