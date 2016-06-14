package com.mgdiez.tweetstat.injector.component;

import android.app.Activity;

import com.mgdiez.tweetstat.injector.PerActivity;
import com.mgdiez.tweetstat.injector.module.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation: {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    // Publicly available
    Activity getActivity();
}