package in.avimarine.rcrecorder.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
@Entity()
public class Race implements Parcelable {
  @PrimaryKey(autoGenerate = true)
  public int key;
  @ColumnInfo(name = "orc_race_id")
  public int orcRaceId;
  @ColumnInfo(name = "event_key")
  public String eventKey;
  @ColumnInfo(name = "race_name")
  public String raceName;
  @ColumnInfo(name = "startTime")
  public Date start;
  @ColumnInfo(name = "class_id")
  public String classId;
  @ColumnInfo(name = "distance")
  public double distance;
  @ColumnInfo(name = "course_id")
  public int courseId;
  @ColumnInfo(name = "provisional")
  public boolean provisional;
  @ColumnInfo(name = "scoring_tyoe")
  public int scoringType;
  @ColumnInfo(name = "discardable")
  public boolean discardable;
  @ColumnInfo(name = "coeff")
  public double coeff;


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel p, int i) {
    p.writeInt(key);
    p.writeInt(orcRaceId);
    p.writeString(eventKey);
    p.writeString(raceName);
    p.writeLong(start.getTime());
    p.writeString(classId);
    p.writeDouble(distance);
    p.writeInt(courseId);
    p.writeByte((byte) (provisional ? 1 : 0));
    p.writeInt(scoringType);
    p.writeByte((byte) (discardable ? 1 : 0));
    p.writeDouble(coeff);

  }

  public static final Creator<Race> CREATOR =
      new Creator<Race>() {
        public Race createFromParcel(Parcel in) {
          Race boat = new Race();
          boat.key = in.readInt();
          boat.orcRaceId = in.readInt();
          boat.eventKey = in.readString();
          boat.raceName = in.readString();
          boat.start = new Date(in.readLong());
          boat.classId = in.readString();
          boat.distance = in.readDouble();
          boat.courseId = in.readInt();
          boat.provisional = in.readByte() != 0;
          boat.scoringType =  in.readInt();
          boat.discardable = in.readByte() != 0;
          boat.coeff = in.readDouble();
          return boat;
        }

        @Override
        public Race[] newArray(int size) {
          return new Race[size];
        }
      };
}
