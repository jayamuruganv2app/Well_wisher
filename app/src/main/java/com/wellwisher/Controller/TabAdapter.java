package com.wellwisher.Controller;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.wellwisher.ModalClass.TabModel;
import com.wellwisher.R;
import com.wellwisher.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    private ArrayList<TabModel> tabitemArrayList;
    int index;
    private OnEditTextChanged onEditTextChanged;
    public interface OnEditTextChanged {
        void onTextChanged(int position,View v);
    }
    public TabAdapter(Context context, ArrayList<TabModel> tabitemArrayList,OnEditTextChanged onEditTextChanged) {
        this.tabitemArrayList = tabitemArrayList;
        this.mContext = context;
        this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tab_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.tabTitle.setText(tabitemArrayList.get(position).getTabText());
        holder.tabCount.setText(tabitemArrayList.get(position).getTabCount());

        holder.tabRelativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditTextChanged.onTextChanged(position, view);
                index=position;
                notifyDataSetChanged();
            }
        });

        if(index==position)
        {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
               holder.tabRelativelayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_shape1));
               holder.tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            } else {
                holder.tabRelativelayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_shape1));
                holder.tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            }
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.tabCount.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_count));
                holder.tabCount.setTextColor(ContextCompat.getColor(mContext, R.color.gradientbottom));

            } else {
                holder.tabCount.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_count));
                holder.tabCount.setTextColor(ContextCompat.getColor(mContext, R.color.gradientbottom));

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
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.tabCount.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_count1));
                holder.tabCount.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            } else {
                holder.tabCount.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_count1));
                holder.tabCount.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            }
        }
    }

    @Override
    public int getItemCount() {
        return tabitemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tab_text)
        TextView tabTitle;
        @BindView(R.id.tab_count)
        TextView tabCount;
        @BindView(R.id.tab_relative)
        RelativeLayout tabRelativelayout;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(this);
           // view.setOnClickListener(myClickListener);
            ButterKnife.bind(this, view);

        }
       /* public ViewHolder(View v, View.OnClickListener myClickListener)
        {
            super(v);
            v.setOnClickListener(myClickListener);
        }*/


        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

           /* final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tabRelativelayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_shape1));
                tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            } else {
                tabRelativelayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_shape1));
                tabTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));

            }
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                tabCount.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.tab_count));
                tabCount.setTextColor(ContextCompat.getColor(mContext, R.color.gradientbottom));

            } else {
                tabCount.setBackground(ContextCompat.getDrawable(mContext, R.drawable.tab_count));
                tabCount.setTextColor(ContextCompat.getColor(mContext, R.color.gradientbottom));

            }*/

        }



    }
}

