package in.avimarine.racecommittee.objects;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import in.avimarine.orccertificatesimporter.ORCCertificateType;
import java.util.Date;
import java.util.UUID;

/**
 * This file is part of an
 * Avi Marine Innovations project: sailscore
 * first created by aayaffe on 26/07/2018.
 */
public class Boat implements Parcelable {
  private UUID boatUUID;
  private String RefNo;
  private String SailNo;
  private String YachtsName;
  private String YachtsClass;
  private double LOA;
  private int bowNo;
  private Date checkIn;
  private Date OCS;
  private Date finish;

  public Boat() {
    boatUUID = UUID.randomUUID();
  }

  public UUID getBoatUUID() {
    return boatUUID;
  }

  public void setBoatUUID(String uuid) {
    boatUUID = UUID.fromString(uuid);
  }

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
    p.writeString(boatUUID.toString());
    p.writeString(getRefNo());
    p.writeString(getSailNo());
    p.writeString(getYachtsName());
    p.writeString(getYachtsClass());
    p.writeDouble(getLOA());
    p.writeInt(getBowNo());
  }

  public static final Creator<Boat> CREATOR =
      new Creator<Boat>() {
        public Boat createFromParcel(Parcel in) {
          Boat boat = new Boat();
          boat.setBoatUUID(in.readString());
          boat.setRefNo(in.readString());
          boat.setSailNo(in.readString());
          boat.setYachtsName(in.readString());
          boat.setYachtsClass(in.readString());
          boat.setLOA(in.readDouble());
          boat.setBowNo(in.readInt());
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
}
