package in.avimarine.rcrecorder.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import in.avimarine.rcrecorder.objects.Race;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Dao
public interface RaceDao {
  @Query("SELECT * FROM race")
  LiveData<List<Race>> getAll();

//  @Query("SELECT * FROM boat WHERE boatUUID IN (:boatIds)")
//  ArrayList<Boat> loadAllByIds(int[] boatIds);

  @Query("SELECT * FROM race WHERE event_key LIKE :eventKey")
  public LiveData<List<Race>> findByEventKey(String eventKey);

//  @Query("SELECT * FROM boat WHERE bow_no LIKE :bowNo LIMIT 1")
//  LiveData<Boat> findByBowNo(String bowNo);

//  @Query("SELECT * FROM boat WHERE yachts_name LIKE :boatName LIMIT 1")
//  LiveData<Boat> findByBoatName(String boatName);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(Race... races);

  @Update
  void update(Race race);

  @Delete
  void delete(Race race);

  @Query("DELETE FROM race")
  void deleteAllRaces();

}
