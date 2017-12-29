package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.iposapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentOptionsFragment extends Fragment {

    private Button newButton, editButton;

    public PaymentOptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_payment_options,
                container,
                false
        );

        newButton = (Button)view.findViewById(R.id.button_new_payment);
        editButton = (Button)view.findViewById(R.id.button_edit_payments);

        newButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ClientsMultipleCrepFragment fragment = new ClientsMultipleCrepFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                        fragment,
                        fragment.getTag()).commit();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
