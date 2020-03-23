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
import br.com.envolvedesenvolve.alcoolaqui.R;

public class SobreFragment extends DialogFragment {

    protected View view;
    private Activity parent;
    private Button btn_cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sobre, container, false);
//        this.getDialog().setTitle(parent.getString(R.string.title_activity_about));

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

//        TextView textView = view.findViewById(R.id.about_app_version);
        TextView textView = view.findViewById(R.id.text_share);
        textView.setText(versionName + " v" + versionCode);

//        btn_cancel = view.findViewById(R.id.btn_cancel);
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getFragmentManager().popBackStack();
////                    Log.d(TAG, "Click cancel !");
//            }
//        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        parent = activity;
        super.onAttach(activity);
    }
}
