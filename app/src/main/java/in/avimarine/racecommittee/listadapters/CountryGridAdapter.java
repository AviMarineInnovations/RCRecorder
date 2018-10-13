package in.avimarine.racecommittee.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Country;
import java.util.HashSet;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class CountryGridAdapter extends ArrayAdapter<Country> {

  private static final String TAG = "CountryGridAdapter";

  private final Context context;
  private Country[] values;
  private final int tab;

  public CountryGridAdapter(Context context, Country[] values, int i, IdType it) {
    super(context, -1, values);
    this.context = context;
    this.values = values;
    this.tab = i;
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
    Country o = values[position];
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    firstLine.setText(o.code);
    return rowView;
  }

  @Nullable
  @Override
  public Country getItem(int position) {
    if (values==null || position>values.length){
      Log.e(TAG,"Error getting item in position");
      return null;
    }
    return values[position];
  }
  @Override
  public int getCount() {
    if (values!=null) {
      return values.length;
    }
    return 0;
  }

  public void setCountries(List<Country> countries) {
    values = countries.toArray(new Country[countries.size()]);
  }
  public void setCountries(HashSet<Country> countries) {
    values = countries.toArray(new Country[countries.size()]);
  }
}


