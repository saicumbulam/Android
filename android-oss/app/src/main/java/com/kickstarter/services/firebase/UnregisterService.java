package com.kickstarter.services.firebase;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.iid.FirebaseInstanceId;

import timber.log.Timber;

public class UnregisterService extends JobService {
  public static final String UNREGISTER_SERVICE = "Unregister-service";

  @Override
  public boolean onStartJob(final JobParameters job) {
    Timber.d("onStartJob");
    new Thread(() -> {
      try {
        FirebaseInstanceId.getInstance().deleteInstanceId();
        Timber.d("Deleted token");
      } catch (final Exception e) {
        Timber.e("Failed to delete token: %s", e);
      }
    });

    return false;
  }

  @Override
  public boolean onStopJob(final JobParameters job) {
    return false;
  }

}
