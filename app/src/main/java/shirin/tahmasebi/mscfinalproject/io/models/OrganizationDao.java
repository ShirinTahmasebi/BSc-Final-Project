package shirin.tahmasebi.mscfinalproject.io.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ORGANIZATION".
*/
public class OrganizationDao extends AbstractDao<Organization, Long> {

    public static final String TABLENAME = "ORGANIZATION";

    /**
     * Properties of entity Organization.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property No = new Property(2, Long.class, "no", false, "NO");
        public final static Property Website = new Property(3, String.class, "website", false, "WEBSITE");
        public final static Property PhoneNumber = new Property(4, String.class, "phoneNumber", false, "PHONE_NUMBER");
        public final static Property SmsNumber = new Property(5, String.class, "smsNumber", false, "SMS_NUMBER");
        public final static Property Created = new Property(6, String.class, "created", false, "CREATED");
        public final static Property Updated = new Property(7, String.class, "updated", false, "UPDATED");
        public final static Property Logo = new Property(8, String.class, "logo", false, "LOGO");
        public final static Property EmailAddress = new Property(9, String.class, "emailAddress", false, "EMAIL_ADDRESS");
    };


    public OrganizationDao(DaoConfig config) {
        super(config);
    }
    
    public OrganizationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ORGANIZATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT NOT NULL ," + // 1: name
                "\"NO\" INTEGER," + // 2: no
                "\"WEBSITE\" TEXT NOT NULL ," + // 3: website
                "\"PHONE_NUMBER\" TEXT," + // 4: phoneNumber
                "\"SMS_NUMBER\" TEXT," + // 5: smsNumber
                "\"CREATED\" TEXT," + // 6: created
                "\"UPDATED\" TEXT," + // 7: updated
                "\"LOGO\" TEXT NOT NULL ," + // 8: logo
                "\"EMAIL_ADDRESS\" TEXT);"); // 9: emailAddress
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ORGANIZATION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Organization entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        Long no = entity.getNo();
        if (no != null) {
            stmt.bindLong(3, no);
        }
        stmt.bindString(4, entity.getWebsite());
 
        String phoneNumber = entity.getPhoneNumber();
        if (phoneNumber != null) {
            stmt.bindString(5, phoneNumber);
        }
 
        String smsNumber = entity.getSmsNumber();
        if (smsNumber != null) {
            stmt.bindString(6, smsNumber);
        }
 
        String created = entity.getCreated();
        if (created != null) {
            stmt.bindString(7, created);
        }
 
        String updated = entity.getUpdated();
        if (updated != null) {
            stmt.bindString(8, updated);
        }
        stmt.bindString(9, entity.getLogo());
 
        String emailAddress = entity.getEmailAddress();
        if (emailAddress != null) {
            stmt.bindString(10, emailAddress);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Organization readEntity(Cursor cursor, int offset) {
        Organization entity = new Organization( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // no
            cursor.getString(offset + 3), // website
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phoneNumber
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // smsNumber
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // created
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // updated
            cursor.getString(offset + 8), // logo
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // emailAddress
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Organization entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setNo(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setWebsite(cursor.getString(offset + 3));
        entity.setPhoneNumber(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSmsNumber(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreated(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUpdated(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setLogo(cursor.getString(offset + 8));
        entity.setEmailAddress(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Organization entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Organization entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
