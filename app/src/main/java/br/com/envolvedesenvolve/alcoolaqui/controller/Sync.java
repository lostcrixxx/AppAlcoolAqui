package br.com.envolvedesenvolve.alcoolaqui.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.envolvedesenvolve.alcoolaqui.db.MarksTable;
import br.com.envolvedesenvolve.alcoolaqui.db.ProductTable;
import br.com.envolvedesenvolve.alcoolaqui.model.Marks;
import br.com.envolvedesenvolve.alcoolaqui.model.Product;
import br.com.envolvedesenvolve.alcoolaqui.model.User;

public class Sync {

    Marks marks;

    public void getSyncAll(final Context context){
//        setUserAll(context);
        setProductAll(context);
        setMarkAll(context);
    }

    public void setUserAll(final Context context) {
        Log.e("TAG", "setUserAll");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://alcoolaqui.atspace.eu/users.php";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject obj = null;
                ArrayList<User> prodList = new ArrayList<>();

                if (response != null) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            obj = response.getJSONObject(i);
                            User prod = new User();

//                            prod.setId(Integer.valueOf(obj.getString(ProductTable.COLUMN_ID)));
//                            prod.setFk_user(Integer.valueOf(obj.getString(ProductTable.COLUMN_FK_USER)));
//                            prod.setNome(obj.getString(ProductTable.COLUMN_NOME));
//                            prod.setPorcent(Float.valueOf(obj.getString(ProductTable.COLUMN_PORCENT)));
//                            prod.setTamanho(Float.valueOf(obj.getString(ProductTable.COLUMN_TAMANHO)));
//                            prod.setValor(Float.valueOf(obj.getString(ProductTable.COLUMN_VALOR)));
//                            prod.setTitleLocal(obj.getString(ProductTable.COLUMN_TITLE_LOCAL));
//                            prod.setEndereco(obj.getString(ProductTable.COLUMN_ENDERECO));
//                            prod.setDt_inc(obj.getString(ProductTable.COLUMN_DT_INC));

                            prodList.add(prod);
                        }

                        User prod = new User();
                        prod.setInsert(context, prodList); // Salva no SQLite

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "getUser ERROR: " + error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void setProductAll(final Context context) {
        Log.e("TAG", "setProductAll");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://alcoolaqui.atspace.eu/produto.php";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject obj = null;
                ArrayList<Product> prodList = new ArrayList<>();

                if (response != null) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            obj = response.getJSONObject(i);
                            Product prod = new Product();

                            prod.setId(Integer.valueOf(obj.getString(ProductTable.COLUMN_ID)));
                            prod.setFk_user(Integer.valueOf(obj.getString(ProductTable.COLUMN_FK_USER)));
                            prod.setNome(obj.getString(ProductTable.COLUMN_NOME));
                            prod.setPorcent(Float.valueOf(obj.getString(ProductTable.COLUMN_PORCENT)));
                            prod.setTamanho(Float.valueOf(obj.getString(ProductTable.COLUMN_TAMANHO)));
                            prod.setValor(Float.valueOf(obj.getString(ProductTable.COLUMN_VALOR)));
                            prod.setTitleLocal(obj.getString(ProductTable.COLUMN_TITLE_LOCAL));
                            prod.setEndereco(obj.getString(ProductTable.COLUMN_ENDERECO));
//                            prod.setDt_inc(obj.getString(ProductTable.COLUMN_DT_INC));

                            prodList.add(prod);
                        }

                        Product prod = new Product();
                        prod.setInsert(context, prodList); // Salva no SQLite

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "getProduct ERROR: " + error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void setMarkAll(final Context context) {
        Log.e("TAG", "setMarkAll");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://alcoolaqui.atspace.eu/mark.php";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject obj = null;
                ArrayList<Marks> marksList = new ArrayList<>();

                if (response != null) {
                    try {

                        for (int i = 0; i < response.length(); i++) {
                            obj = response.getJSONObject(i);
                            marks = new Marks();

                            marks.setId(Integer.valueOf(obj.getString(MarksTable.COLUMN_ID)));
                            marks.setFk_product(Integer.valueOf(obj.getString(MarksTable.COLUMN_FK_PRODUCT)));
                            marks.setLat(Double.valueOf(obj.getString(MarksTable.COLUMN_LAT)));
                            marks.setLon(Double.valueOf(obj.getString(MarksTable.COLUMN_LON)));
//                            prod.setDt_inc(obj.getString(ProductTable.COLUMN_DT_INC));

                            marksList.add(marks);
                        }

//                        marks.setInsert(context, marksList); // Salva no SQLite
                        MarksTable marksTable = new MarksTable(context);
                        marksTable.insertValueOnTableMarks(marksList); // Salva no SQLite

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "getMark ERROR: " + error);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }
}
