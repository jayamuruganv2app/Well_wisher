package com.wellwisher.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wellwisher.R;
import com.wellwisher.ModalClass.Searchmodal;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Searchmodal> arraylist;
    ArrayList<String> app_status;
    public ListViewAdapter(Context context, ArrayList<Searchmodal> arraylist, ArrayList<String> app_status) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = arraylist;
        this. app_status=app_status;

    }

    public class ViewHolder {
        TextView name,app_status;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Searchmodal getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.app_status= (TextView) view.findViewById(R.id.app_status);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(arraylist.get(position).getMovieName());
        if(app_status.get(position).equals("1")){
          //  holder.app_status.setVisibility(View.VISIBLE);
        }else {

        }
        return view;
    }


}