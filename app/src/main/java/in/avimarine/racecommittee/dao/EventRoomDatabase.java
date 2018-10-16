package in.avimarine.racecommittee.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import in.avimarine.racecommittee.objects.Event;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Database(entities = {Event.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class EventRoomDatabase extends RoomDatabase {
  public abstract EventDao eventDao();

  private static volatile EventRoomDatabase instance;

  static EventRoomDatabase getDatabase(final Context context) {
    if (instance == null) {
      synchronized (EventRoomDatabase.class) {
        if (instance == null) {
          instance = Room.databaseBuilder(context.getApplicationContext(),
              EventRoomDatabase.class, "event_database")
              .build();
        }
      }
    }
    return instance;
  }
}
