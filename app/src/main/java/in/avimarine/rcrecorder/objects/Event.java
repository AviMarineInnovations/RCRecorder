package in.avimarine.rcrecorder.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
@Entity()
public class Event implements Parcelable {

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  @PrimaryKey(autoGenerate = true)
  private int key;
  @ColumnInfo(name = "event_name")
  @NonNull
  public String EventName;
  public Event() {
    EventName = "";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel p, int i) {
    p.writeInt(key);
    p.writeString(EventName);
  }

  public static final Creator<Event> CREATOR =
      new Creator<Event>() {
        public Event createFromParcel(Parcel in) {
          Event e = new Event();
          e.key = (in.readInt());
          e.EventName = in.readString();
          return e;
        }

        @Override
        public Event[] newArray(int size) {
          return new Event[size];
        }
      };
}
