package in.avimarine.rcrecorder.dao;

import android.os.AsyncTask;
import in.avimarine.rcrecorder.OrcscParser;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Event;
import in.avimarine.rcrecorder.objects.Race;
import in.avimarine.rcrecorder.objects.Result;
import java.util.List;

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
    List<Boat> boatList = OrcscParser.getBoats(params[0]);
    Boat[] boats = boatList.toArray(new Boat[boatList.size()]);
    for (Boat b : boats) {
      if (b != null) {
        b.setEventKey(e.getKey());
      }
    }
    List<Race> racesList = OrcscParser.getRaces(params[0]);
    Race[] races = racesList.toArray(new Race[racesList.size()]);
    for (Race r : races) {
      if (r != null) {
        r.eventKey = e.getKey();
      }
    }
    List<Result> resultsList = OrcscParser.getResults(params[0]);
    Result[] results = resultsList.toArray(new Result[resultsList.size()]);
    for (Result r : results) {
      if (r != null) {
        r.setEventKey(e.getKey());
      }
    }
    boatDao.insertAll(boats);
    raceDao.insertAll(races);
    resultDao.insertAll(results);
    return null;
  }
}
