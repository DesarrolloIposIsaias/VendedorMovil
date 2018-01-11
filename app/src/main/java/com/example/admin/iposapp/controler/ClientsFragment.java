package com.example.admin.iposapp.controler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

public class ClientsFragment extends Fragment {

    private Database db;
    private ListView clientsListView;
    private FButton filterButton;
    private EditText filterEditText;
    private ListViewClientsAdapter listViewClientsAdapter;

    public ClientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_clients,
                container, false
        );

        try{

            clientsListView = (ListView)view.findViewById(R.id.list_view_clients);
            filterButton = (FButton)view.findViewById(R.id.button_filter);
            filterEditText = (EditText)view.findViewById(R.id.edit_text_filter);

            db = new Database(getContext());

            db.open();
            ArrayList<Client> clients = Database.clientDao.fetchClients();
            db.close();

            listViewClientsAdapter = new ListViewClientsAdapter(getContext(),clients,this);
            clientsListView.setAdapter(listViewClientsAdapter);

            clientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    CurrentData.setSelectedClient(listViewClientsAdapter.getData().get(i));

                    ClientInfoFragment fragment= new ClientInfoFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragment.show(
                            fragmentManager,
                            "client_info_dialog"
                    );
                }
            });


        }
        catch (Exception e){

            e.printStackTrace();
        }

        return view;
    }
}
