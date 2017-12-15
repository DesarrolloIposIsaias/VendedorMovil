package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.model.SaleDetail;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11/08/2016.
 */
public class ChangeClientDialogFragment extends DialogFragment
{
    private AutoCompleteTextView clients;
    private List<Client> allClients;
    boolean entryAvailable = true;
    private static Database dataBase;
    private Dialog toReturn;


    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        LayoutInflater inflater;
        View view, viewCustom;
        Context context;

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.client_changed_dialog, null);
        context = view.getContext();
        dataBase = new Database(context);



        try
        {
            viewCustom = inflater.inflate(R.layout.custom_item,null);

            TextView customItem = (TextView) viewCustom.findViewById(R.id.autoCompleteItem);

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

            clients = (AutoCompleteTextView)view.findViewById(R.id.clientsChanged);
            clients.setThreshold(1);


            clients.addTextChangedListener(new TextWatcher() {
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
                            dataBase = new Database(getActivity());
                            // dataBase.getCtx().deleteDatabase("iposDb");
                            dataBase.open();
                            //  Database.productDao.updateProducts(getContext());
                            allClients = Database.clientDao.searchByFirstLetter(
                                    clients.getText().toString().charAt(0));

                            String[] clientsAdapter = new String[allClients.size()];
                            for(int i = 0; i < allClients.size(); i++)
                            {

                                clientsAdapter[i] =
                                        allClients.get(i).getNombre() +
                                                "<" +
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
                            ex.printStackTrace();
                        }
                        finally {
                            dataBase.close();
                        }

                        entryAvailable = false;
                    }
                }
            });


            clients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long rowId)
                {
                    //TODO Do something with the selected text

                    dataBase.open();

                    String idClient = clients.getEditableText().toString();
                    String clientName = idClient;
                    idClient = idClient.substring(
                            idClient.indexOf("<") + 1, idClient.indexOf(">"));

                    clientName = clientName.substring(0, clientName.indexOf("<"));

                    String idSale = CurrentData.getSaleId();

                    Database.saleDAO.updateClient(idClient, idSale);
                    Database.saleDetailDAO.updateClient(idClient, idSale);

                    CurrentData.setClientId(idClient);
                    CurrentData.setClientName(clientName);

                    dataBase.close();
                    dismiss();
                    sendResult(1);

                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        toReturn = builder.create();
        return toReturn;
    }
    @Override
    public Dialog getDialog(){
        return toReturn;
    }

    private void sendResult(int REQUEST_CODE)
    {
        try{
            Intent intent = new Intent();
            intent.putExtra("newClient", CurrentData.getClientName());
            getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
        }
        catch (Exception ex)
        {
            Log.d("Error", "sendResult: " + ex.toString());
        }
    }
}

