package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import in.avimarine.rcrecorder.objects.Event;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
public class EventViewModel extends AndroidViewModel {

  private EventRepository mRepository;

  private LiveData<List<Event>> mAllEvents;

  public EventViewModel(Application application) {
    super(application);
    mRepository = new EventRepository(application);
    mAllEvents = mRepository.getmAllEvents();
  }

  public LiveData<List<Event>> getAllEvents() {
    return mAllEvents;
  }

  public void insert(Event event) {
    mRepository.insert(event);
  }

  public void update(Event event) {
    mRepository.update(event);
  }

  public void delete(Event event) {
    mRepository.delete(event);
  }
}

