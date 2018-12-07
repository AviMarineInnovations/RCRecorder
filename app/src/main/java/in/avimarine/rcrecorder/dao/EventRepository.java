package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import in.avimarine.rcrecorder.objects.Event;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class EventRepository {

  private EventDao mEventDao;
  private LiveData<List<Event>> mAllEvents;

  EventRepository(Application application) {
    EventsRoomDatabase db = EventsRoomDatabase.getDatabase(application);
    mEventDao = db.eventDao();
    mAllEvents = mEventDao.getAll();
  }

  LiveData<List<Event>> getmAllEvents() {
    return mAllEvents;
  }

  public void insert(Event event) {
    new insertAsyncTask(mEventDao).execute(event);
  }

  public void delete(Event event) {
    new removeAsyncTask(mEventDao).execute(event);
  }

  public void update(Event event) {
    new updateAsyncTask(mEventDao).execute(event);
  }

  private static class insertAsyncTask extends AsyncTask<Event, Void, Void> {

    private EventDao mAsyncTaskDao;

    insertAsyncTask(EventDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Event... params) {
      mAsyncTaskDao.insertAll(params[0]);
      return null;
    }
  }

  private static class removeAsyncTask extends AsyncTask<Event, Void, Void> {

    private EventDao mAsyncTaskDao;

    removeAsyncTask(EventDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final Event... params) {
      mAsyncTaskDao.delete(params[0]);
      return null;
    }
  }

  private class updateAsyncTask extends AsyncTask<Event, Void, Void> {

    private EventDao mAsyncTaskDao;

    public updateAsyncTask(EventDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Event... boats) {
      mAsyncTaskDao.update(boats[0]);
      return null;
    }
  }
}
