package in.avimarine.rcrecorder.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.general.Utils;
import in.avimarine.rcrecorder.objects.Race;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 20/12/2018.
 */
public class NewRaceListAdapter extends BaseAdapter {

  private List<Race> data = new ArrayList<>();
//  private Set<String> disabledClass = new HashSet<>();


  private List<Race> selected = new ArrayList<>();
  private LayoutInflater inflater;

  public NewRaceListAdapter(Context context) {
//      data = array;
    inflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Object getItem(int i) {
    return data.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    final Race current = data.get(i);

    if (view == null) {
      view = inflater.inflate(R.layout.race_list_item, viewGroup, false);
      TextView firstLine = view.findViewById(R.id.firstLine);
      TextView secondLine = view.findViewById(R.id.secondLine);
      TextView classTv = view.findViewById(R.id.class_tv);
      firstLine.setText(Objects.requireNonNull(current).raceName);
      secondLine.setText(Utils.toSimpleDate(Objects.requireNonNull(current).start));
      classTv.setText(Objects.requireNonNull(current).classId);

    }
    return view;


  }

  @Override
  public boolean isEnabled(int position) {
    return selected.contains(data.get(position)) || !getSelectedClass(selected).contains(data.get(position).classId);
  }

  public void setRaces(List<Race> races) {
    data.clear();
    data.addAll(races);
    notifyDataSetChanged();
  }
//  public void setDisabledClass(Set<String> s) {
//    disabledClass.clear();
//    disabledClass.addAll(s);
//    notifyDataSetChanged();
//  }

  public void setSelected(List<Race> l) {
    selected.clear();
    selected.addAll(l);
    notifyDataSetChanged();
  }

  private HashSet<String> getSelectedClass(List<Race> l) {
    HashSet<String> ret = new HashSet<>();
    for (Race r : l) {
      ret.add(r.classId);
    }
    return ret;
  }


}
