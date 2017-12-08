package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.SaleDetail;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11/08/2016.
 */
public class ExistencesDialogFragment extends DialogFragment
{
    private Database database;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        database = new Database(getActivity().getApplicationContext());
        LayoutInflater inflater;
        View view;
        ListView dialogListView;
        ListViewAdapterExistence adapter;
        ArrayList<String> items;
        Context context;

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.existence_dialog, null);
        context = view.getContext();

        try
        {
            dialogListView = (ListView) view.findViewById(R.id.existenceListView);
            ViewGroup headerViewExistence = (ViewGroup) getActivity().getLayoutInflater().inflate(
                    R.layout.header_row_existence,
                    dialogListView,
                    false
            );

            dialogListView.addHeaderView(headerViewExistence);

            items = new ArrayList<String>();
            adapter = new ListViewAdapterExistence(
                    context,
                    R.layout.row_existence_layout,
                    R.id.product,
                    items);

            List<Product> stocks = CurrentData.getProductList();
            List<SaleDetail> saleDetailList = CurrentData.getDetailProductList();
            Product aux;
            database.open();
            items.clear();
            for (int i = 0; i < stocks.size(); i++)
            {
                aux = Database.productDao.fetchProductById(stocks.get(i).getClave());
                items.add(i,
                        aux.getDescripcion1() + " " + aux.getDescripcion2() + "_" +
                        String.valueOf(saleDetailList.get(i).getAmount()) + "_" +
                        stocks.get(i).getExistencia());
            }
            database.close();
            dialogListView.setAdapter(adapter);
            adapter.notifyDataSetInvalidated();
            dialogListView.invalidateViews();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        return builder.create();
    }
}
