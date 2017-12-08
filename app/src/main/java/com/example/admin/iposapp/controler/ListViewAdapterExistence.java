package com.example.admin.iposapp.controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.iposapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by admin on 05/07/2016.
 */
public class ListViewAdapterExistence extends ArrayAdapter<String>
{
    int vg;
    ArrayList<String> items;
    Context context;

    public ListViewAdapterExistence(Context context, int vg, int id, ArrayList<String> items)
    {
        super(context, vg, id, items);
        this.context = context;
        this.vg = vg;
        this.items = items;
    }

    static class ViewHolder
    {
        public TextView descriptionTxtView;
        public TextView requiredTxtView;
        public TextView existenceTxtView;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        if(rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.descriptionTxtView = (TextView)rowView.findViewById(R.id.product);
            holder.requiredTxtView = (TextView)rowView.findViewById(R.id.required);
            holder.existenceTxtView = (TextView)rowView.findViewById(R.id.existence);
            rowView.setTag(holder);
        }

        String [] itemsList = items.toArray(new String[0]);
        itemsList = itemsList[pos].split("_");
        ViewHolder holder = (ViewHolder)rowView.getTag();
        try
        {
            holder.descriptionTxtView.setText(itemsList[0]);
            holder.requiredTxtView.setText(itemsList[1]);
            holder.existenceTxtView.setText(itemsList[2]);
        }
        catch (Exception ex)
        {
            int i = 0;
        }

        return rowView;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
