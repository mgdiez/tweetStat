package com.mgdiez.tweetstat.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.StatisticModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder> {

    private Context context;

    private List<StatisticModel> items;

    private OnStatisticClickedListener onStatisticClickedListener;


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
        if (model.getSubType() == null || model.getSubType().isEmpty()) {
            holder.query.setVisibility(View.INVISIBLE);
            holder.subType.setVisibility(View.INVISIBLE);
        } else {
            holder.subType.setText(model.getSubType().replace("%23", "#"));
        }
        holder.dateGenerated.setText(model.getDateGenerated());
        holder.nTweets.setText(model.getNTweets());
        holder.dataSource.setText(model.getSelectedOption());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStatisticClickedListener != null) {
                    onStatisticClickedListener.onStatisticClicked(model);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public class StatisticViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.dataSource)
        TextView dataSource;

        @Bind(R.id.subType)
        TextView subType;

        @Bind(R.id.nTweets)
        TextView nTweets;

        @Bind(R.id.dateGenerated)
        TextView dateGenerated;

        @Bind(R.id.txtQuery)
        TextView query;

        public StatisticModel item;

        public View itemView;

        public StatisticViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public interface OnStatisticClickedListener {
        void onStatisticClicked(StatisticModel statisticModel);
    }

    public void setOnStatisticClickedListener(OnStatisticClickedListener onStatisticClickedListener) {
        this.onStatisticClickedListener = onStatisticClickedListener;
    }
}
