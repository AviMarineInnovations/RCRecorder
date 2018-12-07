package in.avimarine.rcrecorder.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Event;
import in.avimarine.rcrecorder.objects.Race;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Database(entities = {Boat.class, Event.class, Race.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class EventsRoomDatabase extends RoomDatabase {
  public abstract BoatDao boatDao();
  public abstract EventDao eventDao();
  public abstract RaceDao raceDao();

  private static volatile EventsRoomDatabase instance;

  static EventsRoomDatabase getDatabase(final Context context) {
    if (instance == null) {
      synchronized (EventsRoomDatabase.class) {
        if (instance == null) {
          instance = Room.databaseBuilder(context.getApplicationContext(),
              EventsRoomDatabase.class, "events_database")
              .build();
        }
      }
    }
    return instance;
  }
}
