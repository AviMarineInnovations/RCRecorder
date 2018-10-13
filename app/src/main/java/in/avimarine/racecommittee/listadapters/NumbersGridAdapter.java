package in.avimarine.racecommittee.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Action;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class NumbersGridAdapter extends ArrayAdapter<Action> {

  private final Context context;
  private final Action[] values;
  private final int tab;

  public NumbersGridAdapter(Context context, Action[] values, int i) {
    super(context, -1, values);
    this.context = context;
    this.values = values;
    this.tab = i;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.grid_item, parent, false);
    Action o = values[position];
    RelativeLayout rl = rowView.findViewById(R.id.gridItem);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
        firstLine.setText(values[position].label);
    return rowView;
  }
}


