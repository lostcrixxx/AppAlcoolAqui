package br.com.envolvedesenvolve.alcoolaqui.controller;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import br.com.envolvedesenvolve.alcoolaqui.MapsNewActivity;

public class SendData {

    private String userURL = "http://alcoolaqui.atspace.eu/adicionarUser.php";
    private String productURL = "http://alcoolaqui.atspace.eu/adicionarProd.php";
    private String markURL = "http://alcoolaqui.atspace.eu/adicionarMark.php";

    public void insertDataUser(final String fk_user, final String nome, final String porcent, final String tamanho, final String valor, final String title_local, final String endereco) {
        Log.e("teste", "teste nome: " + nome);
        StringRequest request = new StringRequest(Request.Method.POST, userURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MapsNewActivity maps = MapsNewActivity.getInstance();
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

    public void insertDataProduct(final String fk_user, final String nome, final String porcent, final String tamanho, final String valor, final String title_local, final String endereco) {
        Log.e("teste", "teste nome: " + nome);
        StringRequest request = new StringRequest(Request.Method.POST, productURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MapsNewActivity maps = MapsNewActivity.getInstance();
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

    public void insertDataMark(final String fk_product, final String lat, final String lon) {
        Log.e("teste", "teste nome: " + lat + ", " + lon);
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
