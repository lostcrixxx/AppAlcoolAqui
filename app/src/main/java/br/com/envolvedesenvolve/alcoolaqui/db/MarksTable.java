package br.com.envolvedesenvolve.alcoolaqui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.envolvedesenvolve.alcoolaqui.model.Marks;
import br.com.envolvedesenvolve.alcoolaqui.model.User;

/**
 * Created by Cristiano M. on 21/03/2020
 * modifield by Cristiano M. on 22/03/2020
 */

public class MarksTable extends HelperDB{
    private static final String TAG = "MarksTable";

    public static final String TABLE_NAME = "geography_marks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FK_PRODUCT = "fk_product";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";

    public MarksTable(Context context) {
        super(context);
    }

    // Database creation SQL statement
    public static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FK_PRODUCT + " integer, " // todo not null
            + COLUMN_LAT + " real NOT NULL, "
            + COLUMN_LON + " real NOT NULL "
            + ");";

    private final List<String> tableColumns = Arrays.asList(COLUMN_ID);

    public List<String> getColumns() {
        return tableColumns;
    }

    public static String getName() {
        return TABLE_NAME;
    }

    public ArrayList<Marks> getAllMarks() {
        ArrayList<Marks> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Marks prod = new Marks();
                prod.setId(cursor.getInt(cursor.getColumnIndex(MarksTable.COLUMN_ID)));
                prod.setFk_product(cursor.getInt(cursor.getColumnIndex(MarksTable.COLUMN_FK_PRODUCT)));
                prod.setLat(cursor.getDouble(cursor.getColumnIndex(MarksTable.COLUMN_LAT)));
                prod.setLon(cursor.getDouble(cursor.getColumnIndex(MarksTable.COLUMN_LON)));

                notes.add(prod);

            } while (cursor.moveToNext());
        }

        db.close(); // close db connection
        return notes;
    }

    public ArrayList<Marks> getAllUsers() {
        ArrayList<Marks> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Marks mark = new Marks();
                mark.setId(cursor.getInt(cursor.getColumnIndex(MarksTable.COLUMN_ID)));
                mark.setFk_product(cursor.getInt(cursor.getColumnIndex(MarksTable.COLUMN_FK_PRODUCT)));
                mark.setLat(cursor.getDouble(cursor.getColumnIndex(MarksTable.COLUMN_LAT)));
                mark.setLon(cursor.getDouble(cursor.getColumnIndex(MarksTable.COLUMN_LON)));

                notes.add(mark);

            } while (cursor.moveToNext());
        }

        db.close(); // close db connection
        return notes;
    }

    public void setValuesDatabase(Context context, ContentValues cv) {
        boolean resp = insertValueOnTable(TABLE_NAME, cv);
        if(resp){
            Log.i(TAG, "Localização cadastrada!");
        } else {
            Log.i(TAG, "ERRO localização não cadastrada");
        }
    }
}