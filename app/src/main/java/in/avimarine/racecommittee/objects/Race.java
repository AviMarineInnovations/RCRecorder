package in.avimarine.racecommittee.objects;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.UUID;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
public class Race implements Parcelable {
  private UUID Uuid;
  public int RaceId;
  public String RaceName;
  public Date StartTime;
  public String ClassId;
  public double Distance;
  public int CourseId;
  public boolean Provisional;
  public int ScoringType;
  public boolean Discardable;
  public double Coeff;

  public Race() {
    Uuid = Uuid.randomUUID();
  }

  public UUID getUuid() {
    return Uuid;
  }

  public void setUuid(String uuid) {
    Uuid = Uuid.fromString(uuid);
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel p, int i) {
    p.writeString(Uuid.toString());
    p.writeInt(RaceId);
    p.writeString(RaceName);
    p.writeLong(StartTime.getTime());
    p.writeString(ClassId);
    p.writeDouble(Distance);
    p.writeInt(CourseId);
    p.writeByte((byte) (Provisional ? 1 : 0));
    p.writeInt(ScoringType);
    p.writeByte((byte) (Discardable ? 1 : 0));
    p.writeDouble(Coeff);

  }

  public static final Creator<Race> CREATOR =
      new Creator<Race>() {
        public Race createFromParcel(Parcel in) {
          Race boat = new Race();
          boat.setUuid(in.readString());
          boat.RaceId = in.readInt();
          boat.RaceName = in.readString();
          boat.StartTime = new Date(in.readLong());
          boat.ClassId = in.readString();
          boat.Distance = in.readDouble();
          boat.CourseId = in.readInt();
          boat.Provisional = in.readByte() != 0;
          boat.ScoringType=  in.readInt();
          boat.Discardable = in.readByte() != 0;
          boat.Coeff = in.readDouble();
          return boat;
        }

        @Override
        public Race[] newArray(int size) {
          return new Race[size];
        }
      };
}
