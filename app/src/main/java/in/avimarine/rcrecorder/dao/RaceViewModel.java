package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import in.avimarine.rcrecorder.objects.Race;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
  public class RaceViewModel extends AndroidViewModel {

    private RaceRepository mRepository;

    private LiveData<List<Race>> mAllRaces;

    public RaceViewModel(Application application) {
      super(application);
      mRepository = new RaceRepository(application);
      mAllRaces = mRepository.getAllRaces();
    }

    public LiveData<List<Race>> getAllRaces() { return mAllRaces; }

    public void insert(Race race) { mRepository.insert(race); }

  public void updtae(Race race) { mRepository.update(race); }

    public void delete(Race race) {mRepository.delete(race);}

  public LiveData<List<Race>> getRacesByEventKey(String eventKey) {
    return mRepository.getRacesByEventKey(eventKey);
  }
}

