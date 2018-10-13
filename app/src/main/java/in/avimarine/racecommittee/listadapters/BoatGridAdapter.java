package in.avimarine.racecommittee.listadapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Boat;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class BoatGridAdapter extends ArrayAdapter<Boat> {

  private static final String TAG = "BoatGridAdapter";

  private final Context context;
  private Boat[] values;
  private final int tab;
  private IdType idType;

  public BoatGridAdapter(Context context, int i, IdType it) {
    super(context, -1);
    this.context = context;
    this.tab = i;
    this.idType = it;
  }

  @Override
  public int getCount() {
    if (values!=null) {
      return values.length;
    }
    return 0;
  }

  @Nullable
  @Override
  public Boat getItem(int position) {
    if (values==null || position>values.length){
      Log.e(TAG,"Error getting item in position");
      return null;
    }
    return values[position];
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView;
    if (convertView==null)
      rowView = inflater.inflate(R.layout.grid_item, parent, false);
    else
      rowView=convertView;
    Boat o = values[position];
    RelativeLayout rl = rowView.findViewById(R.id.gridItem);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    switch(idType){
      case BOAT_NAME:
        firstLine.setText(values[position].getYachtsName());
        break;
      case BOW_NO:
        firstLine.setText(values[position].getBowNo()+"");
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

  public void setBoats(List<Boat> boats) {
    values = boats.toArray(new Boat[boats.size()]);
  }

  public IdType getIdType() {
    return idType;
  }

  public void setIdType(IdType idType) {
    this.idType = idType;
  }
}


