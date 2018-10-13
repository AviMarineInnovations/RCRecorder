package in.avimarine.racecommittee.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Country;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class CountryGridAdapter extends ArrayAdapter<Country> {

  private final Context context;
  private final Country[] values;
  private final int tab;
//  private final IdType idType;

  public CountryGridAdapter(Context context, Country[] values, int i, IdType it) {
    super(context, -1, values);
    this.context = context;
    this.values = values;
    this.tab = i;
//    this.idType = it;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.grid_item, parent, false);
    Country o = values[position];
//    RelativeLayout rl = rowView.findViewById(R.id.gridItem);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    firstLine.setText(o.code);
    return rowView;
  }
}


