package com.kickstarter.services.apirequests;

import android.os.Parcelable;

import com.kickstarter.libs.qualifiers.AutoGson;

import androidx.annotation.Nullable;
import auto.parcel.AutoParcel;

@AutoGson
@AutoParcel
public abstract class BackingBody implements Parcelable {
  public abstract int backerCompletedAt();
  public abstract @Nullable String backerNote();
  public abstract long id();
  public abstract long backer();

  @AutoParcel.Builder
  public abstract static class Builder {
    public abstract Builder backerCompletedAt(int __);
    public abstract Builder backerNote(String __);
    public abstract Builder id(long __);
    public abstract Builder backer(long __);
    public abstract BackingBody build();
  }

  public static Builder builder() {
    return new AutoParcel_BackingBody.Builder();
  }

  public abstract Builder toBuilder();
}
