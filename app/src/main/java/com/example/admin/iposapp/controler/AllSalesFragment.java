package com.example.admin.iposapp.controler;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllSalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllSalesFragment extends BaseFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Database dataBase;
    private ListViewAllSalesAdapter  lvAdapter;
    private ArrayList<String> items;
    private ListView allSalesView;
    private AlertDialog alertD;
    private View promptView;
    private Button cancel,modify,deploy;


    public AllSalesFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllSalesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllSalesFragment newInstance(String param1, String param2)
    {
        AllSalesFragment fragment = new AllSalesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_sales, container, false);
        View rowLayout = inflater.inflate(R.layout.row_allsales_layout, container, false);

        promptView = inflater.inflate(R.layout.all_sales_customdialog, null);

        cancel = (Button) promptView.findViewById(R.id.deleteBtn);
        modify = (Button) promptView.findViewById(R.id.modifyBtn);
        deploy = (Button) promptView.findViewById(R.id.deployBtn);

        alertD = new AlertDialog.Builder(getContext()).create();
        alertD.setView(promptView);

        allSalesView = (ListView) view.findViewById(R.id.lVAllSales);
        ViewGroup headerView = (ViewGroup)getActivity().getLayoutInflater().inflate(
                R.layout.header_row_allsales_layout, allSalesView, false);
        allSalesView.addHeaderView(headerView);

        items = new ArrayList<String>();
        lvAdapter = new ListViewAllSalesAdapter (
                getContext(), R.layout.row_allsales_layout, R.id.description, items);
        allSalesView.setAdapter(lvAdapter);

        dataBase = new Database(getContext());

        dataBase.open();
        List<Sale> allSalesList = Database.saleDAO.fetchAllSales();

        for(int i = 0; i < allSalesList.size(); i++)
        {
            String clientName = Database.clientDao.fetchClientById(
                    allSalesList.get(i).getClient()).getNombre();

            items.add(0, allSalesList.get(i).getId() + "_" +
            clientName + "_" +
            allSalesList.get(i).getTotal() + "_" +
            allSalesList.get(i).getSend());
            if(i != 0)
            {
                rowLayout.setBackgroundResource(R.color.blue_normal);
            }
            else
            {
                rowLayout.setBackgroundResource(R.color.blue_pressed);
            }
        }

        lvAdapter.notifyDataSetInvalidated();
        allSalesView.invalidateViews();
        dataBase.close();
        

        allSalesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {

                // TODO Auto-generated method stub

                if(pos != 0)
                {
                    String item = lvAdapter.getItem(pos-1);
                    String [] itemsList = new String[1];
                    itemsList[0] = item;
                    itemsList = itemsList[0].split("_");
///
                    alertD.show();

                    if(itemsList[3] != null)
                    {
                        if(itemsList[3].equals("N") || itemsList[3].equals("P"))
                        {
                            modify.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    try
                                    {
                                        CurrentData.setOnlyForView(false);
                                        String item = lvAdapter.getItem(pos-1);
                                        String [] itemsList = new String[1];
                                        itemsList[0] = item;
                                        itemsList = itemsList[0].split("_");

                                        CurrentData.setSaleId(itemsList[0]);
                                        CurrentData.setClientName(itemsList[1]);
                                        CurrentData.setTotal(Float.valueOf(itemsList[2]));
                                        CurrentData.setMutable(true);
                                        CurrentData.setCallFromAllSales(true);
                                        CurrentData.setSended(itemsList[3]);
                                        dataBase.open();
                                        CurrentData.setClientId(Database.saleDAO.fecthClientId(
                                                itemsList[0]).getClient());
                                        dataBase.close();

                                        SalesFragment newFragment = new SalesFragment();

                                        FragmentTransaction transaction =
                                                getFragmentManager().beginTransaction();

                                        // Replace whatever is in the fragment_container view
                                        // with this fragment,
                                        // and add the transaction to the back stack
                                        alertD.dismiss();
                                        transaction.replace(R.id.fragmentContainer, newFragment);
                                        transaction.addToBackStack(null);

                                        // Commit the transaction
                                        transaction.commit();

                                    }
                                    catch(Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }

                                }
                            });

                            cancel.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    try
                                    {
                                        String item = lvAdapter.getItem(pos-1);
                                        String [] itemsList = new String[1];
                                        itemsList[0] = item;
                                        itemsList = itemsList[0].split("_");

                                        dataBase.open();
                                        Database.saleDetailDAO.deleteBySaleId(itemsList[0]);
                                        Database.saleDAO.deleteSale(itemsList[0]);
                                        dataBase.close();
                                        items.remove(pos-1);
                                        lvAdapter.notifyDataSetChanged();
                                        alertD.dismiss();
                                        Toast.makeText(
                                                getContext(),
                                                "Venta eliminada, ID:" + itemsList[0],
                                                Toast.LENGTH_LONG).show();


                                    }
                                    catch(Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }

                                }
                            });
                        }
                    }
                }

                deploy.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        try{
                            CurrentData.setOnlyForView(true);
                            String item = lvAdapter.getItem(pos-1);
                            String [] itemsList = new String[1];
                            itemsList[0] = item;
                            itemsList = itemsList[0].split("_");
                            SalesFragment newFragment = new SalesFragment();
                            CurrentData.setSaleId(itemsList[0]);
                            CurrentData.setMutable(false);
                            CurrentData.setCallFromAllSales(true);
                            CurrentData.setSended(itemsList[3]);
                            CurrentData.setTotal(Float.valueOf(itemsList[2]));
                            dataBase.open();
                            Client clientInfo = Database.clientDao.fetchClientById(
                                    Database.saleDAO.fecthClientId(itemsList[0]).getClient());
                            CurrentData.setClientId(clientInfo.getClave());
                            CurrentData.setClientName(clientInfo.getNombre());
                            dataBase.close();

                            FragmentTransaction transaction =
                                    getFragmentManager().beginTransaction();

                            // Replace whatever is in the fragment_container view with this fragment,
                            // and add the transaction to the back stack
                            transaction.replace(R.id.fragmentContainer, newFragment);
                            transaction.addToBackStack(null);
                            alertD.dismiss();
                            // Commit the transaction
                            transaction.commit();

                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }

                    }
                });

                //return true;
            }
        });

        return view;
    }

    @Override
    public void onBackPressed()
    {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
