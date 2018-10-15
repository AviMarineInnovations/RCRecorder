package in.avimarine.racecommittee.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import in.avimarine.racecommittee.objects.Boat;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
@Database(entities = {Boat.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BoatRoomDatabase extends RoomDatabase {
  public abstract BoatDao boatDao();

  private static volatile BoatRoomDatabase instance;

  static BoatRoomDatabase getDatabase(final Context context) {
    if (instance == null) {
      synchronized (BoatRoomDatabase.class) {
        if (instance == null) {
          instance = Room.databaseBuilder(context.getApplicationContext(),
              BoatRoomDatabase.class, "boat_database")
              .build();
        }
      }
    }
    return instance;
  }
}
