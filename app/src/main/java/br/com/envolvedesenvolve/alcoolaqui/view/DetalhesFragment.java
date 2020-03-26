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
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import br.com.envolvedesenvolve.alcoolaqui.R;
import br.com.envolvedesenvolve.alcoolaqui.controller.SendData;
import br.com.envolvedesenvolve.alcoolaqui.model.Marks;

public class DetalhesFragment extends DialogFragment {

    private String nome = "",porcent = "",tamanho = "",valor = "",title_local = "";
    private Marker marker;

    protected Button btn_cancel;
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
        view = inflater.inflate(R.layout.fragment_detalhes, container, false);
//        this.getDialog().setTitle(parent.getString(R.string.title_activity_about));

        getEventInformation();

        btn_cancel = view.findViewById(R.id.btnBack);

        edtNome = (EditText)view.findViewById(R.id.edtProduto);
        Log.e("teste", "teste Edit nome: " + edtNome);
        edtPorcent = (EditText)view.findViewById(R.id.edtPorcent);
        edtTamanho = (EditText)view.findViewById(R.id.edtTamanho);
        edtValor = (EditText)view.findViewById(R.id.edtValor);
        edtTitle_local = (EditText)view.findViewById(R.id.edtLocal);

        edtNome.setText(marker.getTitle());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
//                    Log.d(TAG, "Click cancel !");
            }
        });

        return view;
    }

    private boolean getEventInformation() {
        Bundle args = getArguments();
        if (args != null) {
            String jsonArgs = args.getString("marker");
            marker = new Gson().fromJson(jsonArgs, Marker.class);

//            Log.e("AdicionarFragment", "point: " + point);

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
