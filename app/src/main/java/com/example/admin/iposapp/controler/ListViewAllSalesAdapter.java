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
public class ListViewAllSalesAdapter extends ArrayAdapter<String>
{
    int vg;
    ArrayList<String> items;
    Context context;

    public ListViewAllSalesAdapter(Context context, int vg, int id, ArrayList<String> items)
    {
        super(context, vg, id, items);
        this.context = context;
        this.vg = vg;
        this.items = items;
    }

    static class ViewHolder
    {
        public TextView idSalesTxtView;
        public TextView nameClientTxtView;
        public TextView totalTxtView;
        public TextView statusTxtView;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        if(rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.idSalesTxtView = (TextView)rowView.findViewById(R.id.idSale);
            holder.nameClientTxtView = (TextView)rowView.findViewById(R.id.clientName);
            holder.totalTxtView = (TextView)rowView.findViewById(R.id.total);
            holder.statusTxtView = (TextView)rowView.findViewById(R.id.status);
            rowView.setTag(holder);
        }

        String [] itemsList = items.toArray(new String[0]);
        itemsList = itemsList[pos].split("_");
        ViewHolder holder = (ViewHolder)rowView.getTag();
        try
        {
            holder.idSalesTxtView.setText(itemsList[0]);
            holder.nameClientTxtView.setText(itemsList[1]);
            holder.totalTxtView.setText(itemsList[2]);
            if(itemsList[3].equals("Y"))
            {
                holder.statusTxtView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.checkmark, 0, 0, 0);
            }
            else if(itemsList[3].equals("N"))
            {
                holder.statusTxtView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.deletecheckmark, 0, 0, 0);
            }
            else
            {
                holder.statusTxtView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.pending, 0, 0, 0);
            }

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
