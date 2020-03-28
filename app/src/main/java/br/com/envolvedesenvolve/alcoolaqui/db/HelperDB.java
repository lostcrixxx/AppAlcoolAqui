package br.com.envolvedesenvolve.alcoolaqui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HelperDB extends SQLiteOpenHelper {

    private static final String TAG = "HelperDB";

    static HelperDB mDbHelper;

    public static synchronized HelperDB getInstance(Context context) {
        if (mDbHelper == null) {
            mDbHelper = new HelperDB(context);
        }
        return mDbHelper;
    }

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
        Log.e(TAG, "Criando as tabelas");
        db.execSQL(UserTable.DATABASE_CREATE);
        db.execSQL(ProductTable.DATABASE_CREATE);
        db.execSQL(MarksTable.DATABASE_CREATE);

//        db.execSQL("insert into users (id,nome,email,senha)"
//                + "values(0,'Teste','teste@teste.com.br','123') ;");
//        db.execSQL("insert into product (id,nome,porcent,valor,dt_inc)"
//                + "values(0,'Álcool','70',0,00/00/0000) ;");
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
//        Log.d(TAG, "Inserindo nas tabelas");
//        values.put("dt_inc", mFormat.format(new Date()));
//        values.remove("id");
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    // clear list
    public void deleteListMarks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UserTable.TABLE_NAME, null, null);
        db.delete(ProductTable.TABLE_NAME, null, null);
        db.delete(MarksTable.TABLE_NAME, null, null);
        db.close();
    }
}
