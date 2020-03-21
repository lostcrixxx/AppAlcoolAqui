package br.com.envolvedesenvolve.alcoolaqui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.envolvedesenvolve.alcoolaqui.model.User;

public class HelperDB extends SQLiteOpenHelper {

    private static final String TAG = "database";

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_database.db";
    private static final String TABLE_USER = UserTable.getName();
    private static final String TABLE_PRODUCT = ProductTable.getName();
    private static final String TABLE_MARKS = MarksTable.getName();

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.DATABASE_CREATE);
        db.execSQL(ProductTable.DATABASE_CREATE);
        db.execSQL(MarksTable.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "Atualizando tabelas");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARKS);
        onCreate(db);
    }

    public boolean insertValueOnTable(String table, ContentValues values) {
//        values.put("dt_inc", mFormat.format(new Date()));
        values.remove("id");
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }
}