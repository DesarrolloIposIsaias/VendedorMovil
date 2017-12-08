package com.example.admin.iposapp.controler;

/**
 * Created by admin on 24/08/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.admin.iposapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 01/07/2016.
 */
public class ProductsArrayAdapter extends ArrayAdapter<String> implements Filterable
{
    int vg;
    ArrayList<String> items;
    ArrayList<String> suggestions;
    Context context;

    public ProductsArrayAdapter(Context context, int vg, int id, ArrayList<String> items)
    {
        super(context, vg, id, items);
        this.context = context;
        this.vg = vg;
        this.items = items;
        this.suggestions = new ArrayList<>();
    }

    static class ViewHolder
    {
        public TextView autocomplete;
        public TextView existence;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View rowView = convertView;
        if(rowView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.autocomplete = (TextView)rowView.findViewById(R.id.autoCompleteItem);
            holder.existence = (TextView)rowView.findViewById(R.id.existence);
            rowView.setTag(holder);
        }

        String [] itemsList = items.toArray(new String[0]);
         itemsList = itemsList[pos].split("_");
        ViewHolder holder = (ViewHolder)rowView.getTag();
        try
        {
            holder.autocomplete.setText(itemsList[0]);
            holder.existence.setText(itemsList[1]);

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

    /**********************************************************************************************/

    /*Filter filter = new Filter()
    {
        @Override
        public CharSequence convertResultToString(Object resultValue)
        {

            return resultValue.toString();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            if (constraint != null)
            {
                if(suggestions != null)
                {
                    suggestions.clear();
                }
                for (int i = 0; i < items.size(); i++)
                {
                    String product = items.get(i);
                    if (product.toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();

                return filterResults;

            } else
            {
                return new Filter.FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            items = (ArrayList<String>) results.values;
            if(results.count > 0)
            {
                for (String p : items)
                {
                    add(p);
                }
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public Filter getFilter()
    {
        return filter;
    }*/
}

