package in.avimarine.rcrecorder.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import in.avimarine.rcrecorder.objects.Event;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Dao
public interface EventDao {
  @Query("SELECT * FROM event")
  LiveData<List<Event>> getAll();

  @Query("SELECT * FROM event WHERE name LIKE :eventName LIMIT 1")
  LiveData<Event> findByEventName(String eventName);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(Event... events);

  @Update
  void update(Event e);

  @Delete
  void delete(Event e);

  @Query("DELETE FROM event")
  void deleteAll();

}
