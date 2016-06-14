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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.TweetModel;
import com.mgdiez.tweetstat.view.fragment.util.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for tweets list.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetViewHolder> {

    private Context context;

    private List<TweetModel> items;

    public TweetsAdapter(Context context, List<TweetModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_adapter, parent, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        final TweetModel model = items.get(position);
        holder.item = model;
        holder.title.setText(model.getTweet());

        // Profile Image
        Picasso.with(context)
                .load(model.getThumbnailUrl())
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .transform(new CircleTransformation())
                .centerCrop()
                .fit()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }


    public class TweetViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.thumbnail)
        ImageView thumbnail;

        @Bind(R.id.title)
        TextView title;

        public TweetModel item;

        public View itemView;

        public TweetViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
