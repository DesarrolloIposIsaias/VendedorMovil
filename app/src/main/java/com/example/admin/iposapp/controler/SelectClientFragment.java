package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.utility.CurrentData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectClientFragment extends BaseFragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private AutoCompleteTextView clients;
    private List<Client> allClients;
    boolean entryAvailable = true;
    private static Database dataBase;
    private TextView customItem;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SelectClientFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectClientFragment newInstance(String param1, String param2)
    {
        SelectClientFragment fragment = new SelectClientFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_client, container, false);
        View viewCustom = inflater.inflate(R.layout.custom_item, container, false);

        customItem = (TextView) viewCustom.findViewById(R.id.autoCompleteItem);
        customItem.setMaxLines(1);
        customItem.setHorizontalScrollBarEnabled(true);
        customItem.setMovementMethod(new ScrollingMovementMethod());
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean isLarger;

                isLarger = ((TextView) v).getLineCount()
                        * ((TextView) v).getLineHeight() > v.getHeight();
                if (event.getAction() == MotionEvent.ACTION_MOVE
                        && isLarger) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                } else {
                    v.getParent().requestDisallowInterceptTouchEvent(false);

                }
                return false;
            }
        };

        customItem.setOnTouchListener(listener);


        clients = (AutoCompleteTextView)view.findViewById(R.id.clientsAutoCompleteTextView);
        clients.setThreshold(1);


        clients.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(clients.getText().toString().length() == 0)
                {
                    entryAvailable = true;
                }
                if(entryAvailable && clients.getText().toString().length() == 1)
                {

                    try
                    {

                        dataBase = new Database(getContext());
                       // dataBase.getCtx().deleteDatabase("iposDb");
                        dataBase.open();
                      //  Database.productDao.updateProducts(getContext());
                        allClients = Database.clientDao.searchByFirstLetter(
                                clients.getText().toString().charAt(0));
                        dataBase.close();


                        String[] clientsAdapter = new String[allClients.size()];
                        for(int i = 0; i < allClients.size(); i++)
                        {

                            clientsAdapter[i] =
                                    allClients.get(i).getNombre() + "<" +
                                    allClients.get(i).getClave() + ">";
                        }
                        ArrayAdapter<String> toSet = new ArrayAdapter<>(
                                getActivity(),
                                R.layout.custom_item,
                                R.id.autoCompleteItem,
                                clientsAdapter);

                        clients.setAdapter(toSet);
                        CurrentData.setMutable(true);
                    }
                    catch (Exception ex)
                    {
                        int i = 0;
                    }

                    entryAvailable = false;
                }
            }
        });


        clients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                //TODO Do something with the selected text

                dataBase = new Database(getContext());
                dataBase.open();

                Sale sale = new Sale();
                String idClient = clients.getEditableText().toString();
                Client actualClient = Database.clientDao.fetchClientById(
                        idClient.substring(idClient.indexOf("<") + 1, idClient.indexOf(">")));

                if(actualClient.getClave() == null)
                {
                    Toast.makeText(getContext(),"ID INEXISTENTE", Toast.LENGTH_LONG).show();
                }
                else
                {
                    CurrentData.setClientId(idClient.substring(idClient.indexOf("<") + 1, idClient.indexOf(">")));
                    CurrentData.setClientName(actualClient.getNombre());
                    sale.setClient(actualClient.getClave());
                    CurrentData.setSended("N");
                    sale.setSend("N");
                    Database.saleDAO.addSale(sale);
                    sale = Database.saleDAO.fetchLastInserted();
                    CurrentData.setSaleId(sale.getId());
                    Toast.makeText(getContext(),
                            "ID EXISTENTE: "+ actualClient.getClave(),
                            Toast.LENGTH_LONG).show();
                    SalesFragment newFragment = new SalesFragment();
                    // consider using Java coding conventions (upper first char class names!!!)
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.fragmentContainer, newFragment, "fragment_sales");
                    transaction.addToBackStack("fragment_sales");

                    // Commit the transaction
                    transaction.commit();
                    getActivity().getSupportFragmentManager().executePendingTransactions();
                };
                dataBase.close();

            }
        });


        return view;
    }



    @Override
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().popBackStack();
    }



}
