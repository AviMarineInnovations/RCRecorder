package in.avimarine.rcrecorder.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import java.util.Date;

/**
 * This file is part of an
 * Avi Marine Innovations project: sailscore
 * first created by aayaffe on 26/07/2018.
 */
@Entity()
public class Boat implements Parcelable {

  @ColumnInfo(name = "ref_no")
  private String RefNo;
  @PrimaryKey
  @ColumnInfo(name = "sail_no")
  @NonNull
  private String SailNo;
  @ColumnInfo(name = "yachts_name")
  private String YachtsName;
  @ColumnInfo(name = "yachts_class")
  private String YachtsClass;
  @ColumnInfo(name = "loa")
  private double LOA;
  @ColumnInfo(name = "bow_no")
  private int bowNo;
  @ColumnInfo(name = "check_in")
  private Date checkIn;
  @ColumnInfo(name = "ocs")
  private Date OCS;
  @ColumnInfo(name = "finish")
  private Date finish;
  @ColumnInfo(name = "event_key")
  private String eventKey;

  public int getBowNo() {
    return bowNo;
  }

  public void setBowNo(int bowNo) {
    this.bowNo = bowNo;
  }

  public String getRefNo() {
    return RefNo;
  }

  public void setRefNo(String refNo) {
    RefNo = refNo;
  }

  public String getSailNo() {
    return SailNo;
  }

  public void setSailNo(String sailNo) {
    SailNo = sailNo;
  }

  public String getYachtsName() {
    return YachtsName;
  }

  public void setYachtsName(String yachtsName) {
    YachtsName = yachtsName;
  }

  public String getYachtsClass() {
    return YachtsClass;
  }

  public void setYachtsClass(String yachtsClass) {
    YachtsClass = yachtsClass;
  }

  public double getLOA() {
    return LOA;
  }

  public void setLOA(double LOA) {
    this.LOA = LOA;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel p, int i) {
    p.writeString(getRefNo());
    p.writeString(getSailNo());
    p.writeString(getYachtsName());
    p.writeString(getYachtsClass());
    p.writeDouble(getLOA());
    p.writeInt(getBowNo());
    p.writeString(eventKey);
  }

  public static final Creator<Boat> CREATOR =
      new Creator<Boat>() {
        public Boat createFromParcel(Parcel in) {
          Boat boat = new Boat();
          boat.setRefNo(in.readString());
          boat.setSailNo(in.readString());
          boat.setYachtsName(in.readString());
          boat.setYachtsClass(in.readString());
          boat.setLOA(in.readDouble());
          boat.setBowNo(in.readInt());
          boat.setEventKey(in.readString());
          return boat;
        }

        @Override
        public Boat[] newArray(int size) {
          return new Boat[size];
        }
      };

  public void setCheckIn(Date checkIn) {
    this.checkIn = checkIn;
  }

  public Date getCheckIn() {
    return checkIn;
  }

  public void setOCS(Date ocs) {
    this.OCS = ocs;
  }

  public Date getOCS() {
    return OCS;
  }

  public void setFinish(Date finish) {
    this.finish = finish;
  }

  public Date getFinish() {
    return finish;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }
}
