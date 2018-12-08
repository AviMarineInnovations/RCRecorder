package in.avimarine.rcrecorder.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

/**
 * This file is part of an Avi Marine Innovations project: sailscore first created by aayaffe on
 * 26/07/2018.
 */
@Entity()
public class Boat {

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
  @ColumnInfo(name = "yacht_id")
  private int yachtId;
  @ColumnInfo(name = "class_id")
  private String classId;
  @ColumnInfo(name = "div_id")
  private String divId;
  @ColumnInfo(name = "cdl")
  private double cdl;
  @ColumnInfo(name = "gph")
  private double gph;
  @ColumnInfo(name = "osn")
  private double osn;
  @ColumnInfo(name = "ctod")
  private double ctod;
  @ColumnInfo(name = "ctot")
  private double ctot;
  @ColumnInfo(name = "owner")
  private String owner;
  @ColumnInfo(name = "skipper")
  private String skipper;
  @ColumnInfo(name = "sponsor")
  private String sponsor;
  @ColumnInfo(name = "club")
  private String club;
  @ColumnInfo(name = "nation")
  private String nation;
  @ColumnInfo(name = "email")
  private String email;
  @ColumnInfo(name = "phone")
  private String phone;
  @ColumnInfo(name = "cert_type")
  private String certType;
  @ColumnInfo(name = "issue_date")
  private Date issueDate;
  @ColumnInfo(name = "time_limit_secs")
  private int timeLimitSecs;
  @ColumnInfo(name = "nat_auth")
  private String natAuth;
  @ColumnInfo(name = "bin")
  private String BIN;
  @ColumnInfo(name = "points")
  private double points;
  @ColumnInfo(name = "position")
  private int position;
  @ColumnInfo(name = "pts_hash")
  private String ptsHash;
  @ColumnInfo(name = "pts_hash2")
  private String ptsHash2;
  @ColumnInfo(name = "rms")
  private String rms;
  @ColumnInfo(name = "ilcwa")
  private double ilcwa;


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

  public Date getFinish() {
    return finish;
  }

  public void setFinish(Date finish) {
    this.finish = finish;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public int getYachtId() {
    return yachtId;
  }

  public void setYachtId(int yid) {
    yachtId = yid;
  }

  public String getClassId() {
    return classId;
  }

  public void setClassId(String classId) {
    this.classId = classId;
  }

  public String getDivId() {
    return divId;
  }

  public void setDivId(String divId) {
    this.divId = divId;
  }

  public double getCdl() {
    return cdl;
  }

  public void setCdl(double cdl) {
    this.cdl = cdl;
  }

  public double getGph() {
    return gph;
  }

  public void setGph(double gph) {
    this.gph = gph;
  }

  public double getCtod() {
    return ctod;
  }

  public void setCtod(double ctod) {
    this.ctod = ctod;
  }

  public double getCtot() {
    return ctot;
  }

  public void setCtot(double ctot) {
    this.ctot = ctot;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getSkipper() {
    return skipper;
  }

  public void setSkipper(String skipper) {
    this.skipper = skipper;
  }

  public String getSponsor() {
    return sponsor;
  }

  public void setSponsor(String sponsor) {
    this.sponsor = sponsor;
  }

  public double getOsn() {
    return osn;
  }

  public void setOsn(double osn) {
    this.osn = osn;
  }

  public String getClub() {
    return club;
  }

  public void setClub(String club) {
    this.club = club;
  }

  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCertType() {
    return certType;
  }

  public void setCertType(String certType) {
    this.certType = certType;
  }

  public Date getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(Date issueDate) {
    this.issueDate = issueDate;
  }

  public int getTimeLimitSecs() {
    return timeLimitSecs;
  }

  public void setTimeLimitSecs(int timeLimitSecs) {
    this.timeLimitSecs = timeLimitSecs;
  }

  public String getNatAuth() {
    return natAuth;
  }

  public void setNatAuth(String natAuth) {
    this.natAuth = natAuth;
  }

  public String getBIN() {
    return BIN;
  }

  public void setBIN(String bin) {
    this.BIN = bin;
  }

  public double getPoints() {
    return points;
  }

  public void setPoints(double points) {
    this.points = points;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getPtsHash() {
    return ptsHash;
  }

  public void setPtsHash(String ptsHash) {
    this.ptsHash = ptsHash;
  }

  public String getPtsHash2() {
    return ptsHash2;
  }

  public void setPtsHash2(String ptsHash2) {
    this.ptsHash2 = ptsHash2;
  }

  public String getRms() {
    return rms;
  }

  public void setRms(String rms) {
    this.rms = rms;
  }

  public double getIlcwa() {
    return ilcwa;
  }

  public void setIlcwa(double ilcwa) {
    this.ilcwa = ilcwa;
  }
}

