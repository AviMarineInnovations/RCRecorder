package in.avimarine.rcrecorder.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.Date;
import java.util.UUID;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 03/10/2018.
 */
@Entity()
public class Event implements Parcelable {

  private static final String TAG = "Event";
  @NonNull
  @PrimaryKey()
  private String key;
  @ColumnInfo(name = "name")
  @NonNull
  public String name;
  @ColumnInfo(name = "start")
  private Date start;
  @ColumnInfo(name = "end")
  private Date end;
  @ColumnInfo(name = "organizing_authority")
  private String organizingAuthority;
  public Event() {
    name = "";
    key = UUID.randomUUID().toString();
    Log.d(TAG,"New event instantiated with key= " + key);
  }

  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public Date getStart() {
    return start;
  }
  public void setStart(Date start) {
    this.start = start;
  }
  public Date getEnd() {
    return end;
  }
  public void setEnd(Date end) {
    this.end = end;
  }
  public String getOrganizingAuthority() {
    return organizingAuthority;
  }
  public void setOrganizingAuthority(String organizingAuthority) {
    this.organizingAuthority = organizingAuthority;
  }
  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel p, int i) {
    p.writeString(key);
    p.writeString(name);
    p.writeLong(start.getTime());
    p.writeLong(end.getTime());
    p.writeString(organizingAuthority);
  }

  public static final Creator<Event> CREATOR =
      new Creator<Event>() {
        public Event createFromParcel(Parcel in) {
          Event e = new Event();
          e.key = in.readString();
          e.name = in.readString();
          e.start = new Date(in.readLong());
          e.end = new Date(in.readLong());
          e.organizingAuthority = in.readString();
          return e;
        }

        @Override
        public Event[] newArray(int size) {
          return new Event[size];
        }
      };
}
