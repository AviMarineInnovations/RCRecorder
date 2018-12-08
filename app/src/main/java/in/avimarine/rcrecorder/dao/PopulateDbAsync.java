package in.avimarine.rcrecorder.dao;

import android.os.AsyncTask;
import in.avimarine.rcrecorder.OrcscParser;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Event;
import in.avimarine.rcrecorder.objects.Race;
import in.avimarine.rcrecorder.objects.Result;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class PopulateDbAsync extends AsyncTask<String, Void, Void> {

  private final BoatDao boatDao;
  private final RaceDao raceDao;
  private final ResultDao resultDao;
  private final Event e;


  public PopulateDbAsync(EventsRoomDatabase db, Event e) {
    boatDao = db.boatDao();
    raceDao = db.raceDao();
    resultDao = db.resultDao();
    this.e = e;

  }

  @Override
  protected Void doInBackground(final String... params) {
    Boat[] boats = OrcscParser.getBoats(params[0]).toArray(new Boat[params.length]);
    for (Boat b : boats){
      b.setEventKey(e.getKey());
    }
    Race[] races = OrcscParser.getRaces(params[0]).toArray(new Race[params.length]);
    for (Race r : races){
      r.eventKey = e.getKey();
    }
    Result[] results = OrcscParser.getResults(params[0]).toArray(new Result[params.length]);
    for (Result r:results){
      r.setEventKey(e.getKey());
    }
    boatDao.insertAll(boats);
    raceDao.insertAll(races);
    resultDao.insertAll(results);
    return null;
  }
}
