package com.wellwisher.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wellwisher.Activity.AddAlertPage;
import com.wellwisher.Activity.AlertForEveryOneActivity;
import com.wellwisher.Activity.LoginActivity;
import com.wellwisher.ModalClass.EventModule;
import com.wellwisher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    private ArrayList<EventModule> eventitemArrayList;
    int index;

    public EventAdapter(Context context, ArrayList<EventModule> eventitemArrayList) {
        this.eventitemArrayList = eventitemArrayList;
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.eventperson.setText(eventitemArrayList.get(position).getName());
        holder.eventdate.setText(eventitemArrayList.get(position).getDate());
        holder.eventday.setText(eventitemArrayList.get(position).getDay());

        if(position%2==0)
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.list_bg_2));

            } else {
                holder.linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.list_bg_2));

            }

        }
        else
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.linearLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.list_bg_1));

            } else {
                holder.linearLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.list_bg_1));

            }
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent i = new Intent(mContext, AlertForEveryOneActivity.class);
                Intent i = new Intent(mContext, AddAlertPage.class);
                mContext.startActivity(i);
            }
        });
       /* holder.tabRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=position;
                notifyDataSetChanged();
            }
        });*/

        if(index==position)
        {
           /* final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.tabRelativelayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_shape1));
                holder.tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            } else {
                holder.tabRelativelayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_shape1));
                holder.tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            }


        }
        else{
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.tabRelativelayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_shape));
                holder.tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.gradientbottom));

            } else {
                holder.tabRelativelayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_shape));
                holder.tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.gradientbottom));

            }
*/

        }
    }

    @Override
    public int getItemCount() {
        return eventitemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.event_person_name)
        TextView eventperson;
        @BindView(R.id.event_date)
        TextView eventdate;
        @BindView(R.id.event_day)
        TextView eventday;
        @BindView(R.id.linear_layout)
        LinearLayout linearLayout;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);

        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();


        }



    }
}