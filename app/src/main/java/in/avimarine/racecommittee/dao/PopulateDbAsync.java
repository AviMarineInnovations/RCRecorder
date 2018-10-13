package in.avimarine.racecommittee.dao;

import android.os.AsyncTask;
import in.avimarine.racecommittee.objects.Boat;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class PopulateDbAsync extends AsyncTask<Boat, Void, Void> {

  private final BoatDao mDao;


  public PopulateDbAsync(BoatRoomDatabase db) {
    mDao = db.boatDao();
  }

  @Override
  protected Void doInBackground(final Boat... params) {
    mDao.deleteAll();
    mDao.insertAll(params);
    return null;
  }
}
