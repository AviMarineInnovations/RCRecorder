package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import in.avimarine.rcrecorder.objects.Boat;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
  public class BoatViewModel extends AndroidViewModel {

    private BoatRepository mRepository;

    private LiveData<List<Boat>> mAllBoats;

    public BoatViewModel(Application application) {
      super(application);
      mRepository = new BoatRepository(application);
      mAllBoats = mRepository.getAllBoats();
    }

    public LiveData<List<Boat>> getAllBoats() { return mAllBoats; }

    public void insert(Boat boat) { mRepository.insert(boat); }

  public void update(Boat boat) { mRepository.update(boat); }

    public void delete(Boat boat) {mRepository.delete(boat);}

  public LiveData<List<Boat>> getBoatsByEventId(String eventId) {
    return mRepository.getBoatsByEventId(eventId);
  }

  public LiveData<List<Boat>> getBoatsByEventIdAndClassId(String eventId, String classId) {
    return mRepository.getBoatsByEventIdAndClassId(eventId,classId);
  }

  public LiveData<List<Boat>> getBoatsByEventIdAndClassIds(String eventId,String[] classIds) {
    return mRepository.getBoatsByEventIdAndClassIds(eventId,classIds);
  }
}

