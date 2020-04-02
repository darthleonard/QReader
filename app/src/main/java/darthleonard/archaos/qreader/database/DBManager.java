package darthleonard.archaos.qreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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

    public boolean Exist(String code) {
        open();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_CODES + " WHERE " + DatabaseHelper.COLUMN_CODE + " = ?",
                new String[] { code });
        boolean flag = cursor.moveToFirst();
        close();
        return flag;
    }

    public ArrayList<String> GetAll() {
        open();
        ArrayList<String> result = new ArrayList<String>();
        Cursor cursor = database.rawQuery(
                "SELECT " + DatabaseHelper.COLUMN_CODE + " FROM " + DatabaseHelper.TABLE_CODES,
                null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(0));
        }
        close();
        return result;
    }

    private DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    private void close() {
        dbHelper.close();
    }
}
