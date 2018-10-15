package in.avimarine.racecommittee.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import in.avimarine.racecommittee.objects.Boat;
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

  public void updtae(Boat boat) { mRepository.update(boat); }

    public void delete(Boat boat) {mRepository.delete(boat);}
  }

