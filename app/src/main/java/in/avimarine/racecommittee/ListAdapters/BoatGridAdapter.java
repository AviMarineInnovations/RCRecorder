package in.avimarine.racecommittee.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Race;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class BoatGridAdapter extends ArrayAdapter<Boat> {

  private final Context context;
  private final Boat[] values;
  private final int tab;
  private final IdType idType;

  public BoatGridAdapter(Context context, Boat[] values, int i, IdType it) {
    super(context, -1, values);
    this.context = context;
    this.values = values;
    this.tab = i;
    this.idType = it;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.grid_item, parent, false);
    Boat o = values[position];
    RelativeLayout rl = rowView.findViewById(R.id.gridItem);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    switch(idType){
      case BOAT_NAME:
        firstLine.setText(values[position].getYachtsName());
        break;
      case BOW_NO:
        firstLine.setText(values[position].getBowNo());
        break;
      case SAIL_NO:
        firstLine.setText(values[position].getSailNo());
        break;
    }
    if (tab == 1) {
      if (o.getCheckIn() != null) {
        rl.setBackgroundColor(Color.CYAN);
      } else {
        rl.setBackgroundColor(Color.GRAY);
      }
    } else if (tab == 2) {
      if (o.getOCS() != null) {
        rl.setBackgroundColor(Color.CYAN);
      } else {
        rl.setBackgroundColor(Color.GRAY);
      }
    } else {
      if (o.getFinish() != null) {
        rl.setBackgroundColor(Color.CYAN);
      } else {
        rl.setBackgroundColor(Color.GRAY);
      }
    }
    return rowView;
  }
}


