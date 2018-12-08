package in.avimarine.rcrecorder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import in.avimarine.rcrecorder.dao.BoatViewModel;
import in.avimarine.rcrecorder.dao.ResultViewModel;
import in.avimarine.rcrecorder.listadapters.BoatGridAdapter;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Result;
import java.util.Collection;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 08/12/2018.
 */
public class RaceResultsManager {

  List<Result> results;
  List<Boat> boats;
  private BoatViewModel mBoatViewModel;
  private ResultViewModel mResultViewModel;
  private BoatGridAdapter adapter;
  private FragmentActivity fa;


  public RaceResultsManager(FragmentActivity a, BoatGridAdapter adapter, String eventId,
      int raceId, String classId) {
    this.adapter = adapter;
    this.fa = a;
    mBoatViewModel = ViewModelProviders.of(fa).get(BoatViewModel.class);
    mResultViewModel = ViewModelProviders.of(fa).get(ResultViewModel.class);
    mBoatViewModel.getBoatsByEventIdAndClassId(eventId,classId).observe(fa, boats -> {
      // Update the cached copy of the boats in the adapter.
      this.boats = boats;
      notifyUpdate();
    });
    mResultViewModel.getResultsByEventAndRaceId(eventId, raceId).observe(a, results -> {
      // Update the cached copy of the boats in the adapter.
      this.results = results;
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

  public void update(Boat b, int raceId) {
    Result r = findByYachtId(results, b.getYachtId());
    boolean newR = false;
    if (r == null) {
      newR = true;
      r = new Result();
      r.setYachtId(b.getYachtId());
      r.setRaceId(raceId);
      r.setEventKey(b.getEventKey());
      r.setSailNo(b.getSailNo());
    }
    r.setFinishTime(b.getFinish());
    r.setOCS(b.getOCS());
    r.setCheckIn(b.getCheckIn());
    if (newR) {
      mResultViewModel.insert(r);
    } else {
      mResultViewModel.update(r);
    }
    mBoatViewModel.update(b);
  }
}

