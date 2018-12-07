package in.avimarine.rcrecorder.dao;

import android.os.AsyncTask;
import in.avimarine.rcrecorder.OrcscParser;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Event;
import in.avimarine.rcrecorder.objects.Race;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class PopulateDbAsync extends AsyncTask<String, Void, Void> {

  private final BoatDao boatDao;
//  private final EventDao eventDao;
  private final RaceDao raceDao;
  private final Event e;


  public PopulateDbAsync(EventsRoomDatabase db, Event e) {
    boatDao = db.boatDao();
//    eventDao = db.eventDao();
    raceDao = db.raceDao();
    this.e = e;

  }

  @Override
  protected Void doInBackground(final String... params) {
//    Event event = OrcscParser.getEvent(params[0]);
    Boat[] boats = OrcscParser.getBoats(params[0]).toArray(new Boat[params.length]);
    for (Boat b : boats){
      b.setEventKey(e.getKey());
    }
    Race[] races = OrcscParser.getRaces(params[0]).toArray(new Race[params.length]);
    for (Race r : races){
      r.eventKey = e.getKey();
    }
    boatDao.insertAll(boats);
//    eventDao.insertAll(event);
    raceDao.insertAll(races);
    return null;
  }
}
