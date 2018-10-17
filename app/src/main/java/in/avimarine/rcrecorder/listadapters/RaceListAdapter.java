package in.avimarine.rcrecorder.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.objects.Race;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class RaceListAdapter extends ArrayAdapter<Race> {

  private final Context context;
  private final Race[] values;

  public RaceListAdapter(Context context, Race[] values) {
    super(context, -1, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_item, parent, false);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    TextView secondLine = rowView.findViewById(R.id.secondLine);
    ImageView imageView = rowView.findViewById(R.id.icon);
    firstLine.setText(values[position].RaceName);
    secondLine.setText(values[position].StartTime.toString());
    // change the icon for Windows and iPhone
//      String s = values[position];
//      if (s.startsWith("iPhone")) {
//        imageView.setImageResource(R.drawable.no);
//      } else {
//        imageView.setImageResource(R.drawable.ok);
//      }

    return rowView;
  }
}


