package in.avimarine.rcrecorder.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import in.avimarine.rcrecorder.objects.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Dao
public interface ResultDao {
  @Query("SELECT * FROM result")
  LiveData<List<Result>> getAll();

//  @Query("SELECT * FROM boat WHERE boatUUID IN (:boatIds)")
//  ArrayList<Boat> loadAllByIds(int[] boatIds);

  @Query("SELECT * FROM result WHERE race_id LIKE :raceId")
  LiveData<List<Result>> findByRaceId(int raceId);

  @Query("SELECT * FROM result WHERE yacht_id LIKE :yachtId")
  LiveData<List<Result>> findByYachtId(int yachtId);

  @Query("SELECT * FROM result WHERE sail_no LIKE :sailNo")
  LiveData<List<Result>> findBySailNo(String sailNo);

  @Query("SELECT * FROM result WHERE event_key LIKE :eventId AND race_id LIKE :raceId")
  LiveData<List<Result>> findByEventKeyAndRaceId(String eventId, int raceId);

  @Query("SELECT * FROM result WHERE event_key LIKE :eventId AND race_id IN (:raceIds)")
  LiveData<List<Result>> findByEventKeyAndRaceIds(String eventId, int[] raceIds);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(Result... results);

  @Update
  void update(Result r);

  @Delete
  void delete(Result r);

  @Query("DELETE FROM result")
  void deleteAll();



}
