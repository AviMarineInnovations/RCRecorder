package in.avimarine.rcrecorder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import in.avimarine.rcrecorder.dao.BoatViewModel;
import in.avimarine.rcrecorder.dao.RaceViewModel;
import in.avimarine.rcrecorder.dao.ResultViewModel;
import in.avimarine.rcrecorder.general.Utils;
import in.avimarine.rcrecorder.listadapters.BoatGridAdapter;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Race;
import in.avimarine.rcrecorder.objects.Result;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 08/12/2018.
 */
public class RaceResultsManager {

  private static final String TAG = "RaceResultsManager";
  List<Result> results;
  List<Boat> boats;
  List<Race> races;
  private BoatViewModel mBoatViewModel;
  private ResultViewModel mResultViewModel;
  private RaceViewModel mRaceViewModel;
  private BoatGridAdapter adapter;
  private FragmentActivity fa;


  public RaceResultsManager(FragmentActivity a, BoatGridAdapter adapter, String eventId,
      ArrayList<Integer> raceIds) {
    this.adapter = adapter;
    this.fa = a;
    mBoatViewModel = ViewModelProviders.of(fa).get(BoatViewModel.class);
    mResultViewModel = ViewModelProviders.of(fa).get(ResultViewModel.class);
    mRaceViewModel = ViewModelProviders.of(fa).get(RaceViewModel.class);

    mResultViewModel.getResultsByEventAndRaceIds(eventId, Utils.getIntArray(raceIds))
        .observe(a, results -> {
          // Update the cached copy of the boats in the adapter.
          this.results = results;
          notifyUpdate();
        });

    mRaceViewModel.getRacesByRaceIds(eventId, Utils.getIntArray(raceIds)).observe(a, results -> {
      // Update the cached copy of the boats in the adapter.
      this.races = results;
      mBoatViewModel.getBoatsByEventIdAndClassIds(eventId, Utils.getStringArray(getClassIds(races)))
          .observe(fa, boats -> {
            // Update the cached copy of the boats in the adapter.
            this.boats = boats;
            notifyUpdate();
          });
      notifyUpdate();
    });
  }

  private static Result findByYachtId(Collection<Result> list, int YID) {
    for (Result r : list) {
      if (r.getYachtId() == YID) {
        return r;
      }
    }
    return null;
  }

  private List<String> getClassIds(List<Race> races) {
    List<String> ret = new ArrayList<>();
    for (Race r : races) {
      ret.add(r.classId);
    }
    return ret;
  }

  private synchronized void notifyUpdate() {
    if (boats != null && results != null) {
      for (Boat b : boats) {
        Result r = findByYachtId(results, b.getYachtId());
        if (r != null) {
          b.setFinish(r.getFinishTime());
          b.setOCS(r.getOCS());
          b.setCheckIn(r.getCheckIn());
        }
      }
      adapter.setBoats(boats);
      sendTabAndSortBroadcast();
    }

  }

  private void sendTabAndSortBroadcast() {
    Intent intent = new Intent("TABANDSORT");
    fa.sendBroadcast(intent);
  }

  public void update(Boat b) {
    Result r = findByYachtId(results, b.getYachtId());
    boolean newR = false;
    if (r == null) {
      newR = true;
      r = new Result();
      r.setYachtId(b.getYachtId());
      int raceId = getRaceId(b);
      if (raceId < 0) {
        Log.e(TAG, "race id not found");
        throw new UnsupportedOperationException("RaceId not found");
      }
      r.setRaceId(raceId);
      r.setEventKey(b.getEventKey());
//      r.setSailNo(b.getSailNo());

    }
    r.setFinishTime(b.getFinish());
    if (r.getFinish()==null){
      r.setFinish(FinishType.OK);
    }
    r.setOCS(b.getOCS());
    r.setCheckIn(b.getCheckIn());
    if (newR) {
      mResultViewModel.insert(r);
    } else {
      mResultViewModel.update(r);
    }
    mBoatViewModel.update(b);
  }

  private int getRaceId(Boat b) {
    for (Race r : races) {
      if (r.classId.equals(b.getClassId())) {
        return r.orcRaceId;
      }
    }
    return -1;
  }
}


