package com.kickstarter.viewmodels.outputs;

import android.util.Pair;

import com.kickstarter.libs.RefTag;
import com.kickstarter.models.Activity;
import com.kickstarter.models.Project;

import java.util.List;

import rx.Observable;

public interface DiscoveryFragmentViewModelOutputs {
  /** Emits a list of projects to display.*/
  Observable<List<Project>> projectList();

  /**
   * Emits when the activity feed should be shown
   */
  Observable<Boolean> showActivityFeed();

  /**
   * Emits an activity for the activity sample view
   */
  Observable<Activity> activity();

  /**
   * Emits when the login tout activity should be shown
   */
  Observable<Boolean> showLoginTout();

  /**
   * Emits a boolean that determines if the onboarding view should be shown
   */
  Observable<Boolean> shouldShowOnboardingView();

  /**
   * Emits a Project and RefTag pair when we should start the {@link com.kickstarter.ui.activities.ProjectActivity}.
   */
  Observable<Pair<Project, RefTag>> startProjectActivity();

  /**
   * Emits an activity when we should start the {@link com.kickstarter.ui.activities.UpdateActivity}.
   */
  Observable<Activity> startUpdateActivity();
}
