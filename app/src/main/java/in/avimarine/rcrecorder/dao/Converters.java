package in.avimarine.rcrecorder.dao;

import android.arch.persistence.room.TypeConverter;
import in.avimarine.rcrecorder.FinishType;
import java.util.Date;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 06/10/2018.
 */
public class Converters {
  @TypeConverter
  public static Date fromTimestamp(Long value) {
    return value == null ? null : new Date(value);
  }

  @TypeConverter
  public static Long dateToTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }

  @TypeConverter
  public static FinishType fromFinishType(Integer value) {
    return value == null ? null : FinishType.valueOf(value);
  }

  @TypeConverter
  public static Integer finishTypeToInt(FinishType ft) {
    return ft == null ? null : ft.getValue();
  }
}