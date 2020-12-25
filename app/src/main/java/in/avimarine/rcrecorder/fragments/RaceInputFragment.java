package in.avimarine.rcrecorder.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import in.avimarine.rcrecorder.IdType;
import in.avimarine.rcrecorder.R;
import in.avimarine.rcrecorder.RaceResultsManager;
import in.avimarine.rcrecorder.listadapters.BoatGridAdapter;
import in.avimarine.rcrecorder.objects.Boat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class RaceInputFragment extends TabFragement {

  private static final String TAG = "RaceInputFragment";
  /**
   * The fragment argument representing the section number for this fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";
  private static final String ARG_EVENT_KEY = "EVENT_KEY";
  private static final String ARG_RACE_ID = "RACE_ID";
  private static final String ARG_CLASS_ID = "CLASS_ID";
  BroadcastReceiver mTabAndSortReceiver;
  private int sectionNumber;
  private IdType sortBy;
  private IdType idType = IdType.SAIL_NO;
  private GridView gridView;
  private BoatGridAdapter adapter;
  private RaceResultsManager raceResultsManager;
  private String eventKey;
  private ArrayList<Integer> raceIds;

  /**
   * Returns a new instance of this fragment for the given section number.
   */
  @Override
  public RaceInputFragment newInstance(int sectionNumber, String eventKey, ArrayList<Integer> raceIds) {
    RaceInputFragment fragment = new RaceInputFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    args.putString(ARG_EVENT_KEY, eventKey);
    args.putIntegerArrayList(ARG_RACE_ID, raceIds);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_race_input, container, false);
    sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
    eventKey = getArguments().getString(ARG_EVENT_KEY);
    raceIds = getArguments().getIntegerArrayList(ARG_RACE_ID);
    mTabAndSortReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        if (b != null)  {
          String sortByString = b.getString("SORTBY");
          sortBy = IdType.valueOf(sortByString);
        }
        generateButtonTable(gridView, idType, sectionNumber, sortBy);
      }
    };
    IntentFilter filter = new IntentFilter("TABANDSORT");
    getActivity().registerReceiver(mTabAndSortReceiver, filter);
    gridView = rootView.findViewById(R.id.boats_grid);
    adapter = new BoatGridAdapter(getContext(), sectionNumber, IdType.BOAT_NAME);
    gridView.setAdapter(adapter);
    addRadioButtonClickListeneres(rootView);
    raceResultsManager = new RaceResultsManager(getActivity(), adapter, eventKey, raceIds);
    generateButtonTable(gridView, idType, sectionNumber, sortBy);
    return rootView;
  }

  @Override
  public void onStop() {
    if (mTabAndSortReceiver != null) {
      try {
        getActivity().unregisterReceiver(mTabAndSortReceiver);
      } catch (Exception e) {
        Log.e(TAG, "Unable to unregister receiver.", e);
      }
    }
    super.onStop();
  }

  private void addRadioButtonClickListeneres(View v) {
    RadioGroup radioGroup = v.findViewById(R.id.btn_lbl_radiogroup);
    radioGroup.setOnCheckedChangeListener(
        (group, checkedId) -> {
          idType = getIdType(checkedId);
          generateButtonTable(gridView, idType, sectionNumber, sortBy);
        }
    );
  }

  private IdType getIdType(int radioId) {
    if (radioId == R.id.identification_radio_sailno) {
      return IdType.SAIL_NO;
    } else if (radioId == R.id.identification_radio_bowno) {
      return IdType.BOW_NO;
    } else {
      return IdType.BOAT_NAME;
    }
  }

  private void generateButtonTable(GridView gv, IdType idType, int tab, IdType sortBy) {
    if (idType != null) {
      adapter.setIdType(idType);
    } else {
      Log.d(TAG, "Got null idtype");
    }
    if (sortBy != null) {
      adapter.setSortBy(sortBy);
    } else {
      Log.d(TAG, "Got null sortby type");
    }
    adapter.notifyDataSetChanged();
    gv.setOnItemClickListener((parent, view, position, id) -> {
      final Boat boat = (Boat) parent.getItemAtPosition(position);
      btnOnClick(tab, boat);
      RelativeLayout rl = view.findViewById(R.id.gridItem);
      setItemBg(boat, rl, tab);
    });
    gv.setOnItemLongClickListener((parent, view, position, l) -> {
      final Boat boat = (Boat) parent.getItemAtPosition(position);
      btnOnLongClick(tab, boat);
      RelativeLayout rl = view.findViewById(R.id.gridItem);
      setItemBg(boat, rl, tab);
      return true;
    });
  }

  private void setItemBg(Boat o, View rl, int tab) {
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
  }

  private void btnOnLongClick(int tab, Boat b) {
    if (tab == 1) {
      b.setCheckIn(null);
    } else if (tab == 2) {
      b.setOCS(null);
    } else {
      b.setFinish(null);
    }
    raceResultsManager.update(b);
  }

  private void btnOnClick(int tab, Boat b) {
    if (tab == 1) {
      if (b.getCheckIn() == null) {
        b.setCheckIn(new Date());
      }
    } else if (tab == 2) {
      if (b.getOCS() == null) {
        b.setOCS(new Date());
      }
    } else {
      if (b.getFinish() == null) {
        b.setFinish(new Date());
      }
    }
    raceResultsManager.update(b);
  }
}
