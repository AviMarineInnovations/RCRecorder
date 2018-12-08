package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import in.avimarine.rcrecorder.objects.Race;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class RaceRepository {

  private RaceDao mRaceDao;
  private LiveData<List<Race>> mAllRaces;

  public RaceRepository(Application application) {
    EventsRoomDatabase db = EventsRoomDatabase.getDatabase(application);
    mRaceDao = db.raceDao();
    mAllRaces = mRaceDao.getAll();
  }

  LiveData<List<Race>> getAllRaces() {
    return mAllRaces;
  }


  public void insert (Race race) {
    new insertAsyncTask(mRaceDao).execute(race);
  }

  public void delete(Race race) {
    new removeAsyncTask(mRaceDao).execute(race);
  }

  public void deleteAll() {
    new removeAllAsyncTask(mRaceDao).execute();
  }

  public void update(Race race) {
    new updateAsyncTask(mRaceDao).execute(race);
  }

  public LiveData<List<Race>> getRacesByEventKey(String eventKey) {
    return mRaceDao.findByEventKey(eventKey);
  }

  private static class insertAsyncTask extends AsyncTask<Race, Void, Void> {

    private RaceDao mAsyncTaskDao;

    insertAsyncTask(RaceDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Race... params) {
      mAsyncTaskDao.insertAll(params[0]);
      return null;
    }
  }
  private static class removeAsyncTask extends AsyncTask<Race, Void, Void> {

    private RaceDao mAsyncTaskDao;

    removeAsyncTask(RaceDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Race... params) {
      mAsyncTaskDao.delete(params[0]);
      return null;
    }
  }

  private static class removeAllAsyncTask extends AsyncTask<Void, Void, Void> {

    private RaceDao mAsyncTaskDao;

    removeAllAsyncTask(RaceDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      mAsyncTaskDao.deleteAll();
      return null;
    }
  }

  private class updateAsyncTask extends AsyncTask<Race, Void, Void>{

    private RaceDao mAsyncTaskDao;
    public updateAsyncTask(RaceDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Race... races) {
      mAsyncTaskDao.update(races[0]);
      return null;
    }
  }
}
