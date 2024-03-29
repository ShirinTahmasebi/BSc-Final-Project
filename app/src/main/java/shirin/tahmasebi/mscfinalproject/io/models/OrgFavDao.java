package shirin.tahmasebi.mscfinalproject.io.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import shirin.tahmasebi.mscfinalproject.io.models.OrgFav;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ORG_FAV".
*/
public class OrgFavDao extends AbstractDao<OrgFav, Long> {

    public static final String TABLENAME = "ORG_FAV";

    /**
     * Properties of entity OrgFav.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property IsFavorite = new Property(1, Boolean.class, "isFavorite", false, "IS_FAVORITE");
        public final static Property No = new Property(2, Long.class, "no", false, "NO");
    };


    public OrgFavDao(DaoConfig config) {
        super(config);
    }
    
    public OrgFavDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ORG_FAV\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"IS_FAVORITE\" INTEGER," + // 1: isFavorite
                "\"NO\" INTEGER);"); // 2: no
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ORG_FAV\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, OrgFav entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Boolean isFavorite = entity.getIsFavorite();
        if (isFavorite != null) {
            stmt.bindLong(2, isFavorite ? 1L: 0L);
        }
 
        Long no = entity.getNo();
        if (no != null) {
            stmt.bindLong(3, no);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public OrgFav readEntity(Cursor cursor, int offset) {
        OrgFav entity = new OrgFav( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0, // isFavorite
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // no
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, OrgFav entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setIsFavorite(cursor.isNull(offset + 1) ? null : cursor.getShort(offset + 1) != 0);
        entity.setNo(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(OrgFav entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(OrgFav entity) {
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
