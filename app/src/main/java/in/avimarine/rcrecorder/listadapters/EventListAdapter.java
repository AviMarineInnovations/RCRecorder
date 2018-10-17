package in.avimarine.rcrecorder.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.objects.Event;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class EventListAdapter extends ArrayAdapter<Event> {

  private final Context context;
  private List<Event> values = new ArrayList<>();

  public EventListAdapter(Context context) {
    super(context, -1);
    this.context = context;
  }

  @Override
  public int getCount() {
    return values.size();
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_item, parent, false);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
//    TextView secondLine = rowView.findViewById(R.id.secondLine);
    firstLine.setText(values.get(position).EventName);
//    secondLine.setText(values[position].StartTime.toString());
    return rowView;
  }

  public void setEvents(List<Event> events) {
    values = events;
  }
}


