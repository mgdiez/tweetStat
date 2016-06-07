package com.mgdiez.tweetstat.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.StatisticModel;
import com.mgdiez.tweetstat.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {

    private Context context;

    private List<StatisticModel> items;

    public StatisticAdapter(Context context, List<StatisticModel> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public StatisticAdapter.StatisticViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistic_adapter, parent, false);
        return new StatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatisticViewHolder holder, int position) {
        final StatisticModel model = items.get(position);
        holder.item = model;
        holder.type.setText(model.getType());
        holder.subType.setText(model.getSubType());
        holder.dateGenerated.setText(model.getDateGenerated());
        holder.nTweets.setText(model.getNTweets());

        // Profile Image
        Picasso.with(context)
                .load(R.drawable.ic_pie_chart_outlined_white_24dp)
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .transform(new CircleTransformation())
                .centerCrop()
                .fit()
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public class StatisticViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.icon)
        ImageView icon;

        @Bind(R.id.type)
        TextView type;

        @Bind(R.id.subType)
        TextView subType;

        @Bind(R.id.nTweets)
        TextView nTweets;

        @Bind(R.id.dateGenerated)
        TextView dateGenerated;

        public StatisticModel item;

        public View itemView;

        public StatisticViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
