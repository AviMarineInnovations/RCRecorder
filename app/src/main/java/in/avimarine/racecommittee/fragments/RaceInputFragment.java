package in.avimarine.racecommittee.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import in.avimarine.racecommittee.IdType;
import in.avimarine.racecommittee.R;
import in.avimarine.racecommittee.activities.RaceInputActivity;
import in.avimarine.racecommittee.dao.BoatViewModel;
import in.avimarine.racecommittee.listadapters.BoatGridAdapter;
import in.avimarine.racecommittee.objects.Boat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RaceInputFragment extends TabFragement {

  private static final String TAG = "RaceInputFragment";
  /**
   * The fragment argument representing the section number for this fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";
  private int sectionNumber;

  private BoatViewModel mBoatViewModel;
  BroadcastReceiver mTabAndSortReceiver;
  private IdType sortBy;
  private IdType idType = IdType.SAIL_NO;
  private GridView gridView;
  private BoatGridAdapter adapter;
  /**
   * Returns a new instance of this fragment for the given section number.
   */
  @Override
  public RaceInputFragment newInstance(int sectionNumber, ArrayList<Boat> list) {
    RaceInputFragment fragment = new RaceInputFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_race_input, container, false);
    sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
    mTabAndSortReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        if (b == null) {
          Log.e(TAG, "intent is null in receiver");
          return;
        }
        String sortByString = b.getString("SORTBY");
        sortBy = IdType.valueOf(sortByString);
        generateButtonTable(gridView, idType, sectionNumber, sortBy);
      }
    };
    IntentFilter filter = new IntentFilter("TABANDSORT");
    getActivity().registerReceiver(mTabAndSortReceiver, filter);
    gridView = rootView.findViewById(R.id.boats_grid);
    adapter = new BoatGridAdapter(getContext(), sectionNumber, IdType.BOAT_NAME);
    gridView.setAdapter(adapter);
    addRadioButtonClickListeneres(rootView);
    mBoatViewModel = ViewModelProviders.of(getActivity()).get(BoatViewModel.class);
    mBoatViewModel.getAllBoats().observe(this, boats -> {
      // Update the cached copy of the boats in the adapter.
      adapter.setBoats(boats);
      generateButtonTable(gridView, idType, sectionNumber, sortBy);
    });
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
    if (idType!=null)
      adapter.setIdType(idType);
    else
      Log.d(TAG, "Got null idtype");
    if (sortBy!=null)
      adapter.setSortBy(sortBy);
    else
      Log.d(TAG, "Got null sortby type");
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
  private  void setItemBg(Boat o, View rl, int tab) {
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

  private void btnOnLongClick(int tab, Boat o) {
    if (tab == 1) {
      o.setCheckIn(null);
    } else if (tab == 2) {
      o.setOCS(null);
    } else {
      o.setFinish(null);
    }
    mBoatViewModel.updtae(o);
  }

  private void btnOnClick(int tab, Boat o) {
    if (tab == 1) {
      if (o.getCheckIn() == null) {
        o.setCheckIn(new Date());
      }
    } else if (tab == 2) {
      if (o.getOCS() == null) {
        o.setOCS(new Date());
      }
    } else {
      if (o.getFinish() == null) {
        o.setFinish(new Date());
      }
    }
    mBoatViewModel.updtae(o);
  }
}
