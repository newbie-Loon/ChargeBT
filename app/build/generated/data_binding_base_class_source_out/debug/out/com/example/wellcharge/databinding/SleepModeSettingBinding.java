// Generated by view binder compiler. Do not edit!
package com.example.wellcharge.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.wellcharge.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SleepModeSettingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button backBtn;

  @NonNull
  public final Switch chargeWhenPlug;

  @NonNull
  public final Switch closeWhenfinish;

  @NonNull
  public final ImageView imageView8;

  @NonNull
  public final ImageView imageView9;

  @NonNull
  public final TextView textView26;

  private SleepModeSettingBinding(@NonNull ConstraintLayout rootView, @NonNull Button backBtn,
      @NonNull Switch chargeWhenPlug, @NonNull Switch closeWhenfinish,
      @NonNull ImageView imageView8, @NonNull ImageView imageView9, @NonNull TextView textView26) {
    this.rootView = rootView;
    this.backBtn = backBtn;
    this.chargeWhenPlug = chargeWhenPlug;
    this.closeWhenfinish = closeWhenfinish;
    this.imageView8 = imageView8;
    this.imageView9 = imageView9;
    this.textView26 = textView26;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SleepModeSettingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SleepModeSettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.sleep_mode_setting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SleepModeSettingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backBtn;
      Button backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.chargeWhenPlug;
      Switch chargeWhenPlug = ViewBindings.findChildViewById(rootView, id);
      if (chargeWhenPlug == null) {
        break missingId;
      }

      id = R.id.closeWhenfinish;
      Switch closeWhenfinish = ViewBindings.findChildViewById(rootView, id);
      if (closeWhenfinish == null) {
        break missingId;
      }

      id = R.id.imageView8;
      ImageView imageView8 = ViewBindings.findChildViewById(rootView, id);
      if (imageView8 == null) {
        break missingId;
      }

      id = R.id.imageView9;
      ImageView imageView9 = ViewBindings.findChildViewById(rootView, id);
      if (imageView9 == null) {
        break missingId;
      }

      id = R.id.textView26;
      TextView textView26 = ViewBindings.findChildViewById(rootView, id);
      if (textView26 == null) {
        break missingId;
      }

      return new SleepModeSettingBinding((ConstraintLayout) rootView, backBtn, chargeWhenPlug,
          closeWhenfinish, imageView8, imageView9, textView26);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}