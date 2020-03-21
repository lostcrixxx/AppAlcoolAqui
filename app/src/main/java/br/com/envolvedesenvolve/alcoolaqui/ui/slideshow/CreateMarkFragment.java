package br.com.envolvedesenvolve.alcoolaqui.ui.slideshow;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import br.com.envolvedesenvolve.alcoolaqui.R;
import br.com.envolvedesenvolve.alcoolaqui.ui.home.HomeFragment;

public class CreateMarkFragment extends Fragment {

    private Button btnBack;
    private Button btnAdd;

    private CreateMarkViewModel createMarkViewModel;
    private FragmentActivity myContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        createMarkViewModel =
                ViewModelProviders.of(this).get(CreateMarkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_mark, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        btnBack = root.findViewById(R.id.btnBack);
        btnAdd = root.findViewById(R.id.btnAdd);

        createMarkViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new HomeFragment());
                ft.commit();
//                Toast.makeText(getContext(), "Teste", Toast.LENGTH_LONG).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Teste", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
}