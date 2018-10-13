package in.avimarine.racecommittee.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import in.avimarine.racecommittee.objects.Boat;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class BoatRepository {

  private BoatDao mBoatDao;
  private LiveData<List<Boat>> mAllBoats;

  BoatRepository(Application application) {
    BoatRoomDatabase db = BoatRoomDatabase.getDatabase(application);
    mBoatDao = db.boatDao();
    mAllBoats = mBoatDao.getAll();
  }

  LiveData<List<Boat>> getAllBoats() {
    return mAllBoats;
  }


  public void insert (Boat boat) {
    new insertAsyncTask(mBoatDao).execute(boat);
  }

  public void delete(Boat boat) {
    new removeAsyncTask(mBoatDao).execute(boat);
  }

  private static class insertAsyncTask extends AsyncTask<Boat, Void, Void> {

    private BoatDao mAsyncTaskDao;

    insertAsyncTask(BoatDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Boat... params) {
      mAsyncTaskDao.insertAll(params[0]);
      return null;
    }
  }
  private static class removeAsyncTask extends AsyncTask<Boat, Void, Void> {

    private BoatDao mAsyncTaskDao;

    removeAsyncTask(BoatDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Boat... params) {
      mAsyncTaskDao.delete(params[0]);
      return null;
    }
  }
}
