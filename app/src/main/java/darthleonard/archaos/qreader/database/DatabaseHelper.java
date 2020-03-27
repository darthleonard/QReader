package darthleonard.archaos.qreader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "qreader.db";
    public static final String TABLE_CODES = "Codes";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_CODE = "Code";
    public static final String COLUMN_NOTES = "Notes";
    public static final String COLUMN_DATE = "CreationDate";
    static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        createCodesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODES);
        onCreate(db);
    }

    private void createCodesTable(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ TABLE_CODES +"("+
                        COLUMN_ID +" integer primary key, "+
                        COLUMN_DATE +" text, "+
                        COLUMN_CODE +" text, "+
                        COLUMN_NOTES +" text)"
        );
    }
}
