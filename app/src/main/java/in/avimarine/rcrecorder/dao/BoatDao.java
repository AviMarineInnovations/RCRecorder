package in.avimarine.rcrecorder.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import in.avimarine.rcrecorder.objects.Boat;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Dao
public interface BoatDao {
  @Query("SELECT * FROM boat")
  LiveData<List<Boat>> getAll();

  @Query("SELECT * FROM boat WHERE sail_no LIKE :sailNo LIMIT 1")
  LiveData<Boat> findBySailNo(String sailNo);

  @Query("SELECT * FROM boat WHERE bow_no LIKE :bowNo LIMIT 1")
  LiveData<Boat> findByBowNo(String bowNo);

  @Query("SELECT * FROM boat WHERE yachts_name LIKE :boatName LIMIT 1")
  LiveData<Boat> findByBoatName(String boatName);

  @Query("SELECT * FROM boat WHERE event_key LIKE :eventId")
  LiveData<List<Boat>> findByEventKey(String eventId);

  @Query("SELECT * FROM boat WHERE event_key LIKE :eventId AND class_id LIKE :classId")
  LiveData<List<Boat>> findByEventKeyAndClassId(String eventId, String classId);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(Boat... boats);

  @Update
  void update(Boat b);

  @Delete
  void delete(Boat boat);

  @Query("DELETE FROM boat")
  void deleteAll();



}
