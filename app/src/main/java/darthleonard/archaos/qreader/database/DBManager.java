package darthleonard.archaos.qreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class DBManager {
    protected SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
    }

    public void Insert(String code, String notes) {
        open();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.COLUMN_DATE, new Date().toString());
        contentValue.put(DatabaseHelper.COLUMN_CODE, code);
        contentValue.put(DatabaseHelper.COLUMN_NOTES, notes);
        database.insert(DatabaseHelper.TABLE_CODES, null, contentValue);
        close();
    }

    public void Delete(int id) {
        open();
        database.delete(
                DatabaseHelper.TABLE_CODES,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        close();
    }

    private DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public boolean Exist(String code) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_CODES + " WHERE name = ?",
                new String[] { code });
        if(cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    private void close() {
        dbHelper.close();
    }
}
