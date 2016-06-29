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
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.mgdiez.tweetstat.R;
import com.mgdiez.tweetstat.injector.HasComponent;

import javax.inject.Inject;

import executor.RxBus;
import executor.events.ConnectionEvent;
import executor.events.NoConnectionEvent;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

    protected CompositeSubscription subscriptions;

    protected String message;

    protected Snackbar snackbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        message = getString(R.string.no_connection);
        subscriptions = new CompositeSubscription();
        initSubscriptions();
    }

    private void initSubscriptions() {
        subscriptions.add(RxBus.getInstance().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof ConnectionEvent) {
                    dismissSnackbar();
                }

                if (o instanceof NoConnectionEvent) {
                    showMessageConnection();
                }
            }
        }));
    }

    protected abstract void dismissSnackbar();

    abstract protected void showMessageConnection();

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

}
