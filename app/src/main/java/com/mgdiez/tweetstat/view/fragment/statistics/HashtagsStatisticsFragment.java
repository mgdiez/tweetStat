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
package com.mgdiez.tweetstat.view.fragment.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.injector.component.StatisticsComponent;
import com.mgdiez.tweetstat.model.StatisticModel;
import com.mgdiez.tweetstat.presenter.HashtagsStatisticsPresenter;
import com.mgdiez.tweetstat.view.activity.FullGraphActivity;
import com.mgdiez.tweetstat.view.adapter.StatisticAdapter;
import com.mgdiez.tweetstat.view.fragment.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HashtagsStatisticsFragment extends BaseFragment implements StatisticAdapter
        .OnStatisticClickedListener {

    @Bind(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @Inject
    public HashtagsStatisticsPresenter hashtagsStatisticsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.hometimeline_statistics_fragment, container, false);
        ButterKnife.bind(this, v);
        initialize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        hashtagsStatisticsPresenter.getHashtags();
        return v;
    }

    private void initialize() {
        this.getComponent(StatisticsComponent.class).inject(this);
        hashtagsStatisticsPresenter.setView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hashtagsStatisticsPresenter.destroy();
    }

    public static Fragment newInstance() {
        return new HashtagsStatisticsFragment();
    }

    @Override
    protected void dismissSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    protected void showMessageConnection() {
        if (recyclerView != null) {
            snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);
            snackbar.getView().setBackgroundColor(getContext().getResources().getColor(R.color
                    .md_red_900));
            snackbar.show();
        }
    }

    public void showMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void setItems(List<StatisticModel> models) {
        StatisticAdapter statisticAdapter = new StatisticAdapter(models);
        statisticAdapter.setOnStatisticClickedListener(this);
        recyclerView.setAdapter(statisticAdapter);
    }


    @Override
    public void onStatisticClicked(StatisticModel statisticModel) {
        if (statisticModel != null) {
            Intent intent = new Intent(getActivity(), FullGraphActivity.class);
            intent.putExtra("id", statisticModel.getId());
            startActivity(intent);
        }
    }
}
