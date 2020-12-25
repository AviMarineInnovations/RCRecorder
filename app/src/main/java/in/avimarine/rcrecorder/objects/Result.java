package in.avimarine.rcrecorder.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import in.avimarine.rcrecorder.FinishType;
import java.util.Date;

/**
 * This file is part of an Avi Marine Innovations project: sailscore first created by aayaffe on
 * 26/07/2018.
 */
@Entity(primaryKeys = {"race_id", "yacht_id", "event_key"})
public class Result {

//  @ColumnInfo(name = "sail_no")
//  private String SailNo;
  @ColumnInfo(name = "yacht_id")
  private int yachtId;
  @ColumnInfo(name = "race_id")
  private int raceId;
  @ColumnInfo(name = "check_in")
  private Date checkIn;
  @ColumnInfo(name = "ocs")
  private Date OCS;
  @ColumnInfo(name = "finish_time")
  private Date finishTime;
  @ColumnInfo(name = "finish")
  private FinishType finish;
  @ColumnInfo(name = "event_key")
  @NonNull
  private String eventKey;
  @ColumnInfo(name = "remarks")
  private String remarks;
  @ColumnInfo(name = "penalty")
  private double penalty;
  public Result() {
    eventKey = "";
  }

  public int getYachtId() {
    return yachtId;
  }

  public void setYachtId(int yachtId) {
    this.yachtId = yachtId;
  }

  public int getRaceId() {
    return raceId;
  }

  public void setRaceId(int raceId) {
    this.raceId = raceId;
  }

  public Date getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Date finishTime) {
    this.finishTime = finishTime;
  }

//  public String getSailNo() {
//    return SailNo;
//  }
//
//  public void setSailNo(String sailNo) {
//    SailNo = sailNo;
//  }

  public Date getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(Date checkIn) {
    this.checkIn = checkIn;
  }

  public Date getOCS() {
    return OCS;
  }

  public void setOCS(Date ocs) {
    this.OCS = ocs;
  }

  public FinishType getFinish() {
    return finish;
  }

  public void setFinish(FinishType finish) {
    this.finish = finish;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public double getPenalty() {
    return penalty;
  }

  public void setPenalty(double penalty) {
    this.penalty = penalty;
  }
}
