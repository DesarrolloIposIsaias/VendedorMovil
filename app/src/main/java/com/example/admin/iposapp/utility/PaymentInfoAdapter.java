package com.example.admin.iposapp.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.admin.iposapp.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 07/09/2016.
 */
public class PaymentInfoAdapter extends BaseExpandableListAdapter
{
    private Context ctx;
    private HashMap<String, List<String>> data;
    private List<String> topics;

    public PaymentInfoAdapter(Context context, HashMap<String, List<String>> data, List<String> topics)
    {
        this.ctx = context;
        this.data = data;
        this.topics = topics;
    }

    @Override
    public int getGroupCount()
    {
        return topics.size();
    }

    @Override
    public int getChildrenCount(int parent)
    {
        return data.get(topics.get(parent)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return topics.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child)
    {
        return data.get(topics.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child)
    {
        return child;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(
            int parent,
            boolean isExpanded,
            View convertView,
            ViewGroup parentView)
    {
        String groupTitle = (String) getGroup(parent);

        if(convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent_payment, parentView, false);
        }

        TextView parentTxtView = (TextView) convertView.findViewById(R.id.txtParentPayment);
        parentTxtView.setTypeface(null, Typeface.BOLD);
        parentTxtView.setText(groupTitle);

        return convertView;
    }

    @Override
    public View getChildView(
            int parent,
            int child,
            boolean isLastChild,
            View convertView,
            ViewGroup parentView)
    {
        String childTitle = (String) getChild(parent, child);

        if(convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child_payment, parentView, false);
        }

        TextView childTxtView = (TextView) convertView.findViewById(R.id.txtChildPayment);
        childTxtView.setText(childTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
