package br.com.envolvedesenvolve.alcoolaqui.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import br.com.envolvedesenvolve.alcoolaqui.R;
import br.com.envolvedesenvolve.alcoolaqui.controller.SendData;
import br.com.envolvedesenvolve.alcoolaqui.model.Marks;

public class AdicionarFragment extends DialogFragment {

    private String nome = "",porcent = "",tamanho = "",valor = "",title_local = "";
    private LatLng point;

    protected Button btn_cancel, btnAdd;
    protected EditText edtNome,edtPorcent,edtTamanho,edtValor,edtTitle_local;

    protected View view;
    private Activity parent;
    private Marks mark = new Marks();

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
        Log.e("teste", "teste Edit nome: " + edtNome);
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

                if(!edtTitle_local.getText().toString().isEmpty()) {
                    nome = edtNome.getText().toString();
                    Log.e("teste", "teste String nome: " + nome);
                    porcent = edtPorcent.getText().toString();
                    tamanho = edtTamanho.getText().toString();
                    valor = edtValor.getText().toString();
                    title_local = edtTitle_local.getText().toString();

                    SendData sendData = new SendData();
                    sendData.insertDataProduct("0", nome, porcent, tamanho, valor, title_local, "0");

                    // TODO retornar ID do produto
                    double lat = point.latitude;
                    double lon = point.longitude;
                    Log.e("teste", "teste lat e lon: " + lat + ", " + lon);
                    sendData.insertDataMark("0", String.valueOf(lat), String.valueOf(lon));

                    getActivity().getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Informe o nome do local", Toast.LENGTH_LONG).show();
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

            Log.e("AdicionarFragment", "point: " + point);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        parent = activity;
        super.onAttach(activity);
    }
}
