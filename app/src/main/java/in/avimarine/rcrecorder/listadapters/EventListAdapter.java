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
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class EventListAdapter extends ArrayAdapter<Event> {

  private final Context context;

  public EventListAdapter(Context context) {
    super(context, -1);
    this.context = context;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_item, parent, false);
    TextView firstLine = rowView.findViewById(R.id.firstLine);
    firstLine.setText(getItem(position).name);
    return rowView;
  }

  public void setEvents(List<Event> events) {
    clear();
    addAll(events);
    notifyDataSetChanged();
  }

  public Event getEvent(int id) {
    return getItem(id);
  }
}


