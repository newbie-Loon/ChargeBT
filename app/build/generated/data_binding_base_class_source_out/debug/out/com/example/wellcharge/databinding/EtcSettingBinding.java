// Generated by view binder compiler. Do not edit!
package com.example.wellcharge.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.wellcharge.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EtcSettingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button backBtn;

  @NonNull
  public final Guideline guideline;

  @NonNull
  public final Guideline guideline2;

  @NonNull
  public final Button ledSetting;

  @NonNull
  public final Button modeSetting;

  @NonNull
  public final Button sleepSetting;

  @NonNull
  public final Button soundSetting;

  @NonNull
  public final Button tempSetting;

  @NonNull
  public final Button timeSetting;

  private EtcSettingBinding(@NonNull ConstraintLayout rootView, @NonNull Button backBtn,
      @NonNull Guideline guideline, @NonNull Guideline guideline2, @NonNull Button ledSetting,
      @NonNull Button modeSetting, @NonNull Button sleepSetting, @NonNull Button soundSetting,
      @NonNull Button tempSetting, @NonNull Button timeSetting) {
    this.rootView = rootView;
    this.backBtn = backBtn;
    this.guideline = guideline;
    this.guideline2 = guideline2;
    this.ledSetting = ledSetting;
    this.modeSetting = modeSetting;
    this.sleepSetting = sleepSetting;
    this.soundSetting = soundSetting;
    this.tempSetting = tempSetting;
    this.timeSetting = timeSetting;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static EtcSettingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EtcSettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.etc_setting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EtcSettingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backBtn;
      Button backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.guideline;
      Guideline guideline = ViewBindings.findChildViewById(rootView, id);
      if (guideline == null) {
        break missingId;
      }

      id = R.id.guideline2;
      Guideline guideline2 = ViewBindings.findChildViewById(rootView, id);
      if (guideline2 == null) {
        break missingId;
      }

      id = R.id.ledSetting;
      Button ledSetting = ViewBindings.findChildViewById(rootView, id);
      if (ledSetting == null) {
        break missingId;
      }

      id = R.id.modeSetting;
      Button modeSetting = ViewBindings.findChildViewById(rootView, id);
      if (modeSetting == null) {
        break missingId;
      }

      id = R.id.sleepSetting;
      Button sleepSetting = ViewBindings.findChildViewById(rootView, id);
      if (sleepSetting == null) {
        break missingId;
      }

      id = R.id.soundSetting;
      Button soundSetting = ViewBindings.findChildViewById(rootView, id);
      if (soundSetting == null) {
        break missingId;
      }

      id = R.id.tempSetting;
      Button tempSetting = ViewBindings.findChildViewById(rootView, id);
      if (tempSetting == null) {
        break missingId;
      }

      id = R.id.timeSetting;
      Button timeSetting = ViewBindings.findChildViewById(rootView, id);
      if (timeSetting == null) {
        break missingId;
      }

      return new EtcSettingBinding((ConstraintLayout) rootView, backBtn, guideline, guideline2,
          ledSetting, modeSetting, sleepSetting, soundSetting, tempSetting, timeSetting);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}