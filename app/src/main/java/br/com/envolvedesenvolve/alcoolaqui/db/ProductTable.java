package br.com.envolvedesenvolve.alcoolaqui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.envolvedesenvolve.alcoolaqui.model.Marks;
import br.com.envolvedesenvolve.alcoolaqui.model.Product;
import br.com.envolvedesenvolve.alcoolaqui.model.User;

/**
 * Created by Cristiano M. on 21/03/2020
 * modifield by Cristiano M. on 22/03/2020
 */

public class ProductTable extends HelperDB {
    private static final String TAG = "ProductTable";

    public static final String TABLE_NAME = "product";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FK_USER = "fk_user";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PORCENT = "porcent";
    public static final String COLUMN_TAMANHO = "tamanho";
    public static final String COLUMN_VALOR = "valor";
    public static final String COLUMN_TITLE_LOCAL = "title_local";
    public static final String COLUMN_ENDERECO = "endereco";
    public static final String COLUMN_DT_INC = "dt_inc";

    public ProductTable(Context context) {
        super(context);
    }

    // Database creation SQL statement
    public static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + "("
            + COLUMN_ID + " integer PRIMARY KEY, "
            + COLUMN_FK_USER + " text, "
            + COLUMN_NOME + " text NOT NULL, "
            + COLUMN_PORCENT + " text NOT NULL, "
            + COLUMN_TAMANHO + " integer, "
            + COLUMN_VALOR + " text, " // NOT NULL
            + COLUMN_TITLE_LOCAL + " text, "
            + COLUMN_ENDERECO + " text, "
            + COLUMN_DT_INC + " text" // NOT NULL
            + ");";

    private final List<String> tableColumns = Arrays.asList(COLUMN_ID);

    public List<String> getColumns() {
        return tableColumns;
    }

    public static String getName() {
        return TABLE_NAME;
    }

    public List<String> getAllNotes() {
        List<String> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
//                Note note = new Note();
//                notes.setId(cursor.getInt(cursor.getColumnIndex(UserTable.COLUMN_ID)));
                notes.add(cursor.getString(0));
                notes.add(cursor.getString(1));
                notes.add(cursor.getString(2));
                notes.add(cursor.getString(3));

            } while (cursor.moveToNext());
        }

        db.close(); // close db connection
        return notes;
    }

    public ArrayList<Product> getAllProds() {
        ArrayList<Product> prodList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product prod = new Product();
                prod.setId(cursor.getInt(cursor.getColumnIndex(ProductTable.COLUMN_ID)));
                prod.setFk_user(cursor.getInt(cursor.getColumnIndex(ProductTable.COLUMN_FK_USER)));
                prod.setNome(cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_NOME)));
                prod.setPorcent(cursor.getFloat(cursor.getColumnIndex(ProductTable.COLUMN_PORCENT)));
                prod.setTamanho(cursor.getFloat(cursor.getColumnIndex(ProductTable.COLUMN_TAMANHO)));
                prod.setValor(cursor.getFloat(cursor.getColumnIndex(ProductTable.COLUMN_VALOR)));
                prod.setTitleLocal(cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_TITLE_LOCAL)));
                prod.setEndereco(cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_ENDERECO)));
                prod.setDt_inc(cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_DT_INC)));

                prodList.add(prod);

            } while (cursor.moveToNext());
        }

        db.close(); // close db connection
        return prodList;
    }

    public void insertValueOnTableMarks(ArrayList<Marks> insp) {
//        Log.i("DatabaseManager", "Teste insert INICIO");

        // you can use INSERT only
        String sql = "INSERT OR REPLACE INTO " +TABLE_NAME+ " ( id, fk_product, lat, lon) VALUES ( ?,?,?,? )";

        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransactionNonExclusive();
        // db.beginTransaction();

        SQLiteStatement stmt = db.compileStatement(sql);

        for (int i = 0; i < insp.size(); i++) {
            stmt.bindLong(1, insp.get(i).getId());
            stmt.bindLong(2, insp.get(i).getFk_product());
            stmt.bindDouble(3, insp.get(i).getLat());
            stmt.bindDouble(4, insp.get(i).getLon());

//            Log.i("DatabaseManager", "Teste insert Data " + insp.get(i).getDt_sync());

            stmt.execute();
            stmt.clearBindings();
//            Log.e("DatabaseManager", "Teste insert PROCESSANDO " + count++);
        }

//        Log.i("DatabaseManager", "Teste insert FIM ");
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    public void setValuesDatabase(Context context, ContentValues cv) {
        boolean resp = insertValueOnTable(TABLE_NAME, cv);
        if(resp){
            Log.i(TAG, "Produto cadastrado!");
        } else {
            Log.i(TAG, "ERRO produto não cadastrado");
        }
    }
}