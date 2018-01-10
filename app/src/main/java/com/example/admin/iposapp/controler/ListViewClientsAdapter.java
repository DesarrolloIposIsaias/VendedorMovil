package com.example.admin.iposapp.controler;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ListViewClientsAdapter
        extends ArrayAdapter<Client>
        implements View.OnClickListener{

    private ArrayList<Client> dataSet;
    private Fragment parent;
    private Context context;
    private Database db;

    private static class ViewHolder{
        TextView txtClientId;
        TextView txtClientName;
        ImageView imgCancel;
    }

    public ListViewClientsAdapter(Context ctx, ArrayList<Client> data, Fragment caller) {
        super(ctx, R.layout.client_custom_row, data);

        dataSet = data;
        context = ctx;
        parent = caller;
        db = new Database(context);
    }

    @Override
    public void onClick(View view) {

        try{

            int position = (Integer) view.getTag();
            Object object = getItem(position);
            Client client = (Client)object;

            CurrentData.setSelectedClient(client);

            switch (view.getId()){

                case R.id.img_cancel:

                    assert client != null;
                    db.open();
                    boolean deleteSuccess = Database.clientDao.deleteClient(client.getClave());
                    db.close();

                    if(!deleteSuccess)
                        throw new Exception("Problema al eliminar el cliente");

                    db.open();
                    ArrayList<Client> payments = Database.clientDao.fetchClients();
                    db.close();

                    dataSet.clear();
                    dataSet.addAll(payments);
                    notifyDataSetChanged();

                    Toast.makeText(context, "Cliente eliminado", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
        catch (Exception e){

            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Client client = getItem(position);

        ListViewClientsAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder =
                    new ListViewClientsAdapter.ViewHolder();

            LayoutInflater inflater =
                    LayoutInflater.from(getContext());

            convertView =
                    inflater.inflate(
                            R.layout.client_custom_row,
                            parent,
                            false
                    );

            viewHolder.txtClientId= (TextView)convertView.findViewById(
                    R.id.text_view_client_id
            );

            viewHolder.txtClientName = (TextView)convertView.findViewById(
                    R.id.text_view_client_name
            );

            viewHolder.imgCancel = (ImageView) convertView.findViewById(
                    R.id.img_cancel
            );

            result = convertView;
            result.setTag(viewHolder);

            if(position % 2 == 1){

                result.setBackgroundColor(Color.LTGRAY);

            }
        }
        else{

            viewHolder = (ListViewClientsAdapter.ViewHolder) convertView.getTag();
            result = convertView;

        }

        assert client != null;
        viewHolder.txtClientId.setText("Clave Cliente: " + client.getClave());
        viewHolder.txtClientName.setText(client.getNombre());
        viewHolder.imgCancel.setOnClickListener(this);
        viewHolder.imgCancel.setTag(position);

        return convertView;
    }

    public List<Client> getData(){
        return dataSet;
    }
}
