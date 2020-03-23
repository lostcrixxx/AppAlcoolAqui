package br.com.envolvedesenvolve.alcoolaqui.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.envolvedesenvolve.alcoolaqui.BuildConfig;
import br.com.envolvedesenvolve.alcoolaqui.MapsNewActivity;
import br.com.envolvedesenvolve.alcoolaqui.R;

public class AdicionarFragment extends DialogFragment {

    protected View view;
    private Activity parent;
    private Button btn_cancel, btnAdd;

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

        btn_cancel = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);

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

                // TODO salvar no banco e enviar para web
                MapsNewActivity maps = MapsNewActivity.getInstance();
                maps.addMarker();

                getActivity().getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        parent = activity;
        super.onAttach(activity);
    }
}
