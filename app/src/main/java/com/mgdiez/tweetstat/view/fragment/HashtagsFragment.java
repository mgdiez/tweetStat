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
package com.mgdiez.tweetstat.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.HashtagModel;
import com.mgdiez.tweetstat.presenter.HashtagsPresenter;
import com.mgdiez.tweetstat.view.adapter.HashtagAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HashtagsFragment extends BaseFragment {

    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.my_recycler_view)
    RecyclerView recyclerView;

    private HashtagsPresenter hashtagsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hashtag_fragment, container, false);
        ButterKnife.bind(this, v);
        hashtagsPresenter = new HashtagsPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hashtagsPresenter.getHashtags();

            }
        });
        hashtagsPresenter.getHashtags();
        return v;
    }

    public static HashtagsFragment newInstance() {
        return new HashtagsFragment();
    }


    public void setItems(List<HashtagModel> models) {
        HashtagAdapter hashtagAdapter = new HashtagAdapter(models);
        recyclerView.setAdapter(hashtagAdapter);
    }

    public void stopRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(true);
        }
    }

    public void startRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setEnabled(false);
        }
    }
}
