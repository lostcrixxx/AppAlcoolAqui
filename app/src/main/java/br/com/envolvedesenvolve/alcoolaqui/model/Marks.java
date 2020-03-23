package br.com.envolvedesenvolve.alcoolaqui.model;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import br.com.envolvedesenvolve.alcoolaqui.db.MarksTable;
import br.com.envolvedesenvolve.alcoolaqui.db.UserTable;

public class Marks {

    private int id;
    private int fk_product;
    private double lat;
    private double lon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_product() {
        return fk_product;
    }

    public void setFk_product(int fk_product) {
        this.fk_product = fk_product;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setInsert(Context context, ArrayList<Marks> prodList){
        ContentValues cv;
        MarksTable table = new MarksTable(context);

        for(Marks markLine : prodList){
            cv = new ContentValues();
            cv.put(MarksTable.COLUMN_ID, markLine.getId());
            cv.put(MarksTable.COLUMN_FK_PRODUCT, markLine.getFk_product());
            cv.put(MarksTable.COLUMN_LAT, markLine.getLat());
            cv.put(MarksTable.COLUMN_LON, markLine.getLon());

            table.setValuesDatabase(context, cv);
        }
    }
}
