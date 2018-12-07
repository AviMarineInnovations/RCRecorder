package in.avimarine.rcrecorder.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.objects.Race;
import java.util.List;
import java.util.Objects;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class RaceListAdapter extends ArrayAdapter<Race> {

  private final Context context;

  public RaceListAdapter(Context context) {
    super(context, -1);
    this.context = context;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_item, parent, false);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    TextView secondLine = rowView.findViewById(R.id.secondLine);
    firstLine.setText(Objects.requireNonNull(getItem(position)).raceName);
    secondLine.setText(Objects.requireNonNull(getItem(position)).start.toString());
    return rowView;
  }

  public void setRaces(List<Race> races) {
    clear();
    addAll(races);
    notifyDataSetChanged();
  }
}


