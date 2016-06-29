/**
 * Copyright (C) 2016 Marc Gonzalez Diez Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mgdiez.tweetstat.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.HashtagModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagViewHolder> {

    private int selectedItem = 0;

    private List<HashtagModel> items;

    public HashtagAdapter(List<HashtagModel> items) {
        this.items = items;
    }

    @Override
    public HashtagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hashtag_adapter,
                parent, false);
        return new HashtagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HashtagViewHolder holder, final int position) {
        final HashtagModel model = items.get(position);
        holder.itemView.setSelected(selectedItem == position);
        holder.item = model;
        holder.hashtagName.setText(model.getName());
        if (model.getNTweets() != -1) {
            holder.nTweets.setText(String.valueOf(model.getNTweets()));
        } else {
            holder.nTweets.setText("--");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedItem);
                selectedItem = position;
                notifyItemChanged(selectedItem);
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

    public int getSelectedItem() {
        return selectedItem;
    }

    public String getHashtagSelectedQuery() {
        return items.get(selectedItem).getQueryName();
    }

    public class HashtagViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.hashtagName)
        TextView hashtagName;

        @Bind(R.id.nTweets)
        TextView nTweets;

        public HashtagModel item;

        public View itemView;

        public HashtagViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
