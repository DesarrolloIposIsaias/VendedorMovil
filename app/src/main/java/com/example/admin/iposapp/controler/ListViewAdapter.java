package com.example.admin.iposapp.controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.iposapp.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by admin on 01/07/2016.
 */
public class ListViewAdapter extends ArrayAdapter<String>
{
    int vg;
    ArrayList<String> items;
    Context context;

    public ListViewAdapter(Context context, int vg, int id, ArrayList<String> items)
    {
        super(context, vg, id, items);
        this.context = context;
        this.vg = vg;
        this.items = items;
    }

    static class ViewHolder
    {
        public TextView descriptionTxtView;
        public TextView amountTxtView;
        public TextView priceTxtView;
        public TextView discountTxtView;
        public TextView subtotalTxtView;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        if(rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.descriptionTxtView = (TextView)rowView.findViewById(R.id.description);
            holder.amountTxtView = (TextView)rowView.findViewById(R.id.amount);
            holder.priceTxtView = (TextView)rowView.findViewById(R.id.price);
            holder.subtotalTxtView = (TextView)rowView.findViewById(R.id.subtotal);
            holder.discountTxtView = (TextView)rowView.findViewById(R.id.descuento);
            rowView.setTag(holder);
        }

        String [] itemsList = items.toArray(new String[0]);
        itemsList = itemsList[pos].split("_");
        ViewHolder holder = (ViewHolder)rowView.getTag();
        try
        {
            holder.descriptionTxtView.setText(itemsList[0]);
            holder.amountTxtView.setText(itemsList[1]);
            holder.priceTxtView.setText(itemsList[2]);
            holder.discountTxtView.setText(itemsList[3]);
            holder.subtotalTxtView.setText(itemsList[4]);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return rowView;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
