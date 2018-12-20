package in.avimarine.rcrecorder.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import in.avimarine.rcrecorder.objects.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 12/10/2018.
 */
  public class ResultViewModel extends AndroidViewModel {

    private ResultRepository mRepository;

    private LiveData<List<Result>> mAllResults;

    public ResultViewModel(Application application) {
      super(application);
      mRepository = new ResultRepository(application);
      mAllResults = mRepository.getAllResults();
    }

    public LiveData<List<Result>> getAllResults() { return mAllResults; }

    public void insert(Result result) { mRepository.insert(result); }

  public void update(Result result) { mRepository.update(result); }

    public void delete(Result result) {mRepository.delete(result);}

  public LiveData<List<Result>> getResultsByEventAndRaceId(String eventId, int raceId) {
    return mRepository.getResultsByEventAndRaceId(eventId,raceId);
  }

  public LiveData<List<Result>> getResultsByEventAndRaceIds(String eventId, int[] raceIds) {
    return mRepository.getResultsByEventAndRaceIds(eventId,raceIds);
  }
}

