package br.com.envolvedesenvolve.alcoolaqui.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import br.com.envolvedesenvolve.alcoolaqui.MapsActivity;
import br.com.envolvedesenvolve.alcoolaqui.R;
import br.com.envolvedesenvolve.alcoolaqui.controller.SendData;
import br.com.envolvedesenvolve.alcoolaqui.controller.Sync;
import br.com.envolvedesenvolve.alcoolaqui.model.Marks;
import br.com.envolvedesenvolve.alcoolaqui.utils.Global;

public class AdicionarFragment extends DialogFragment {

    private static final String TAG = "AdicionarFragment";

    private String nome = "",porcent = "",tamanho = "",valor = "",title_local = "";
    private String idProduct;
    private double lat, lon;
    private LatLng point;

    protected Button btn_cancel, btnAdd;
    protected EditText edtNome,edtPorcent,edtTamanho,edtValor,edtTitle_local;

    protected View view;
    private Activity parent;
    private SharedPreferences prefs;

    private Context context = MapsActivity.getInstance();
    private Marks mark = new Marks();
    private SendData sendData = new SendData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_adicionar, container, false);
//        this.getDialog().setTitle(parent.getString(R.string.title_activity_about));

        getEventInformation();

        btn_cancel = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);

        edtNome = (EditText)view.findViewById(R.id.edtProduto);
//        Log.d(TAG, "teste Edit nome: " + edtNome);
        edtPorcent = (EditText)view.findViewById(R.id.edtPorcent);
        edtTamanho = (EditText)view.findViewById(R.id.edtTamanho);
        edtValor = (EditText)view.findViewById(R.id.edtValor);
        edtTitle_local = (EditText)view.findViewById(R.id.edtLocal);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
//                    Log.d(TAG, "Click cancel !");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (!edtTitle_local.getText().toString().isEmpty()) {
                        nome = edtNome.getText().toString();
//                    Log.i("teste", "teste String nome: " + nome);
                        porcent = edtPorcent.getText().toString();
                        tamanho = edtTamanho.getText().toString();
                        valor = edtValor.getText().toString();
                        title_local = edtTitle_local.getText().toString();

                        sendData.insertDataProduct("0", nome, porcent, tamanho, valor, title_local, "0");

                        lat = point.latitude;
                        lon = point.longitude;
//                    Log.i("teste", "teste lat e lon: " + lat + ", " + lon);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                prefs = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
                                idProduct = String.valueOf(prefs.getInt("idProduct", 0));

//                            Log.d(TAG, "insertDataMark: " + idProduct);
                                sendData.insertDataMark(idProduct, String.valueOf(lat), String.valueOf(lon));
                            }
                        }, 5000);


                        getActivity().getFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getActivity(), "Informe o nome do local", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Toast.makeText(getActivity(), "Não foi possível adicionar o local", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Erro não foi adicionado na WEB " + e.toString());
                }
            }
        });
        return view;
    }

    private boolean getEventInformation() {
        Bundle args = getArguments();
        if (args != null) {
            String jsonArgs = args.getString("point");
            point = new Gson().fromJson(jsonArgs, LatLng.class);

//            Log.d("AdicionarFragment", "point: " + point);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        parent = (Activity) context;
    }
}
