package com.kickstarter.libs;

import android.content.Context;

import com.kickstarter.libs.qualifiers.ApplicationContext;
import com.kickstarter.libs.utils.PlayServicesCapability;
import com.kickstarter.services.firebase.DispatcherKt;
import com.kickstarter.services.firebase.RegisterService;
import com.kickstarter.services.firebase.UnregisterService;

import androidx.annotation.NonNull;



public final class DeviceRegistrar implements DeviceRegistrarType {
  private final @NonNull PlayServicesCapability playServicesCapability;
  private @ApplicationContext @NonNull Context context;

  public static final String TOPIC_GLOBAL = "global";

  public DeviceRegistrar(final @NonNull PlayServicesCapability playServicesCapability,
    final @ApplicationContext @NonNull Context context) {
    this.playServicesCapability = playServicesCapability;
    this.context = context;
  }

  /**
   * If Play Services is available on this device, start a service to register it with Google Cloud Messaging.
   */
  public void registerDevice() {
    if (!this.playServicesCapability.isCapable()) {
      return;
    }
    DispatcherKt.dispatchJob(this.context, RegisterService.class, RegisterService.REGISTER_SERVICE);
  }

  /**
   * If Play Services is available on this device, start a service to unregister it with Google Cloud Messaging.
   */
  public void unregisterDevice() {
    if (!this.playServicesCapability.isCapable()) {
      return;
    }
    DispatcherKt.dispatchJob(this.context, UnregisterService.class, UnregisterService.UNREGISTER_SERVICE);
  }
}
