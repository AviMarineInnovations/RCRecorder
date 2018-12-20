package in.avimarine.rcrecorder.listadapters;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 20/12/2018.
 * This class is useful for using inside of a ListView that needs to have checkable items.
 * Adapted from [sro5h](https://github.com/sro5h/android-multiselect)
 */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
  private CheckBox checkbox;

  public CheckableRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    int childCount = getChildCount();

    // find checked text view
    for (int i = 0; i < childCount; ++i) {
      View view = getChildAt(i);

      if (view instanceof CheckBox) {
        checkbox = (CheckBox) view;
      }
    }
  }

  @Override
  public boolean isChecked() {
    return checkbox != null && checkbox.isChecked();
  }

  @Override
  public void setChecked(boolean checked) {
    if (checkbox != null) {
      checkbox.setChecked(checked);
    }
  }

  @Override
  public void toggle() {
    if (checkbox != null) {
      checkbox.toggle();
    }
  }
}
