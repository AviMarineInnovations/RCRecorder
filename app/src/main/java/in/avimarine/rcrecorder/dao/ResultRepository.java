package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import in.avimarine.rcrecorder.objects.Result;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class ResultRepository {

  private ResultDao mResultDao;
  private LiveData<List<Result>> mAllResults;

  public ResultRepository(Application application) {
    EventsRoomDatabase db = EventsRoomDatabase.getDatabase(application);
    mResultDao = db.resultDao();
    mAllResults = mResultDao.getAll();
  }

  LiveData<List<Result>> getAllResults() {
    return mAllResults;
  }


  public void insert (Result result) {
    new insertAsyncTask(mResultDao).execute(result);
  }

  public void delete(Result result) {
    new removeAsyncTask(mResultDao).execute(result);
  }

  public void deleteAll() {
    new removeAllAsyncTask(mResultDao).execute();
  }

  public void update(Result result) {
    new updateAsyncTask(mResultDao).execute(result);
  }

  public LiveData<List<Result>> getResultsByEventAndRaceId(String eventId, int raceId) {
    return mResultDao.findByEventKeyAndRaceId(eventId,raceId);
  }

  private static class insertAsyncTask extends AsyncTask<Result, Void, Void> {

    private ResultDao mAsyncTaskDao;

    insertAsyncTask(ResultDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Result... params) {
      mAsyncTaskDao.insertAll(params[0]);
      return null;
    }
  }
  private static class removeAsyncTask extends AsyncTask<Result, Void, Void> {

    private ResultDao mAsyncTaskDao;

    removeAsyncTask(ResultDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Result... params) {
      mAsyncTaskDao.delete(params[0]);
      return null;
    }
  }

  private static class removeAllAsyncTask extends AsyncTask<Void, Void, Void> {

    private ResultDao mAsyncTaskDao;

    removeAllAsyncTask(ResultDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      mAsyncTaskDao.deleteAll();
      return null;
    }
  }

  private class updateAsyncTask extends AsyncTask<Result, Void, Void>{

    private ResultDao mAsyncTaskDao;
    public updateAsyncTask(ResultDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Result... results) {
      mAsyncTaskDao.update(results[0]);
      return null;
    }
  }
}
