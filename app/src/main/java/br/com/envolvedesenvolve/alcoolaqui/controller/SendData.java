package br.com.envolvedesenvolve.alcoolaqui.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import br.com.envolvedesenvolve.alcoolaqui.MapsActivity;
import br.com.envolvedesenvolve.alcoolaqui.db.HelperDB;
import br.com.envolvedesenvolve.alcoolaqui.utils.Global;

public class SendData {

    private static final String TAG = "SendData";

    private String userURL = "http://alcoolaqui.atspace.eu/adicionarUser.php";
    private String productURL = "http://alcoolaqui.atspace.eu/adicionarProd.php";
    private String markURL = "http://alcoolaqui.atspace.eu/adicionarMark.php";

    private Context context = MapsActivity.getInstance();
    private int idProduct;
    private SharedPreferences prefs;

    public void insertDataUser(final String fk_user, final String nome, final String porcent, final String tamanho, final String valor, final String title_local, final String endereco) {
        Log.e("teste", "teste nome: " + nome);
        StringRequest request = new StringRequest(Request.Method.POST, userURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MapsActivity maps = MapsActivity.getInstance();
                maps.addMarker();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();

//                parameters.put("id", "0");
                parameters.put("fk_user", fk_user);
                parameters.put("nome", nome);
                parameters.put("porcent", porcent);
                parameters.put("tamanho", tamanho);
                parameters.put("valor", valor);
                parameters.put("title_local", title_local);
                parameters.put("endereco", endereco);

//                hidepDialog();

                return parameters;

            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    public String insertDataProduct(final String fk_user, final String nome, final String porcent, final String tamanho, final String valor, final String title_local, final String endereco) {
//        Log.d("teste", "teste nome: " + nome);
        StringRequest request = new StringRequest(Request.Method.POST, productURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
//                SharedPreferences.Editor ed = prefs.edit();

                Sync sync = new Sync();
                idProduct = sync.getLastIdProduct(MapsActivity.getInstance().getApplication());
//                Log.e(TAG, "teste idProduct " + idProduct);
//                ed.putInt("idProduct", idProduct);
//                ed.commit();

                MapsActivity maps = MapsActivity.getInstance();
                maps.addMarker();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();

//                parameters.put("id", "0");
                parameters.put("fk_user", fk_user);
                parameters.put("nome", nome);
                parameters.put("porcent", porcent);
                parameters.put("tamanho", tamanho);
                parameters.put("valor", valor);
                parameters.put("title_local", title_local);
                parameters.put("endereco", endereco);

//                hidepDialog();

                return parameters;

            }
        };

        AppController.getInstance().addToRequestQueue(request);

        return String.valueOf(idProduct);
    }

    public void insertDataMark(final String fk_product, final String lat, final String lon) {
//        Log.d("teste", "teste lat e lon: " + lat + ", " + lon);
        StringRequest request = new StringRequest(Request.Method.POST, markURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Informar que foi adicionado
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();

//                parameters.put("id", "0");
                parameters.put("fk_product", fk_product);
                parameters.put("lat", lat);
                parameters.put("lon", lon);

//                hidepDialog();

                return parameters;

            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
