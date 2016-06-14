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
package com.mgdiez.tweetstat.view.fragment.tweets;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.model.TweetModel;
import com.mgdiez.tweetstat.presenter.SearchTweetsPresenter;
import com.mgdiez.tweetstat.view.adapter.TweetsAdapter;
import com.mgdiez.tweetstat.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchTweetsFragment extends BaseFragment {

    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.searchRecycler)
    RecyclerView recyclerView;

    @Bind(R.id.edtText)
    EditText editText;

    @Bind(R.id.btnSearch)
    ImageButton btnSearch;

    private SearchTweetsPresenter searchPresenter;

    private String query = "";

    private boolean toggle = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, v);
        searchPresenter = new SearchTweetsPresenter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!query.isEmpty()) {
                    searchPresenter.getTweetsBySearch(query.toLowerCase(), true);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    editText.setText("");
                    btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
                    toggle = false;
                    setItems(new ArrayList<TweetModel>());
                } else {
                    query = editText.getText().toString();
                    if (!query.isEmpty()) {
                        btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_black_24dp));
                        searchPresenter.getTweetsBySearch(query, true);
                        toggle = true;
                    }
                }

            }
        });
        return v;
    }

    public static SearchTweetsFragment newInstance() {
        return new SearchTweetsFragment();
    }


    public void setItems(List<TweetModel> models) {
            TweetsAdapter tweetsAdapter = new TweetsAdapter(getContext(), models);
            recyclerView.setAdapter(tweetsAdapter);
            recyclerView.setVisibility(View.VISIBLE);
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

    public void showMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    public String getQuery() {
        if (query.isEmpty()) {
            showMessage("Can not perform statistic without a query");
        }
        return query;
    }
    @Override
    protected void dismissSnackbar() {
        if (snackbar != null){
            snackbar.dismiss();
        }
    }

    @Override
    protected void showMessageConnection() {
        snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(getContext().getResources().getColor(R.color.md_red_900));
        snackbar.show();
    }

}
