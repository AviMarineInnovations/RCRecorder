package in.avimarine.rcrecorder;

import android.support.annotation.Nullable;
import android.util.Log;
import in.avimarine.orcscorerxmlparser.Orcsc.FleetRow;
import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import in.avimarine.orcscorerxmlparser.Orcsc.RaceRow;
import in.avimarine.orcscorerxmlparser.Orcsc.RsltRow;
import in.avimarine.orcscorerxmlparser.OrcscDeserializer;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Event;
import in.avimarine.rcrecorder.objects.Race;
import in.avimarine.rcrecorder.objects.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class OrcscParser {

  private static final String TAG = "OrcscParser";

  @Nullable
  public static List<Boat> getBoats(String res) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(res);
      return convertFleetToBoats(f);
    } catch (Exception e) {
      Log.e(TAG, "Error parsing orcsc file string.");
    }
    return new ArrayList<>();
  }

  @Nullable
  public static List<Race> getRaces(String res) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(res);

      return convertRacesToRace(f);
    } catch (Exception e) {
      Log.e(TAG, "Error parsing orcsc file string.");
    }
    return new ArrayList<>();
  }

  @Nullable
  public static List<Result> getResults(String res) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(res);

      return convertResultToResult(f);
    } catch (Exception e) {
      Log.e(TAG, "Error parsing orcsc file string.");
    }
    return new ArrayList<>();
  }

  private static ArrayList<Result> convertResultToResult(OrcscFile f) {
    ArrayList<Result> ret = new ArrayList<>();
    if (f == null) {
      return ret;
    }
    for (RsltRow r : f.rslt.list) {
      Result b = convertToResult(r);
      ret.add(b);
    }
    return ret;
  }

  private static Result convertToResult(RsltRow rr) {
    if (rr == null) {
      return null;
    }
    Result r = new Result();
    r.setFinishTime(rr.FinishTime);
    r.setFinish(FinishType.valueOf(rr.Finish));
    r.setRaceId(rr.RaceId);
    r.setYachtId(rr.YID);
    r.setRemarks(rr.Remarks);
    r.setPenalty(rr.Penalty);
    return r;
  }

  private static ArrayList<Race> convertRacesToRace(OrcscFile f) {
    ArrayList<Race> ret = new ArrayList<>();
    if (f == null) {
      return ret;
    }
    for (RaceRow r : f.race.list) {
      Race b = convertToRace(r);
      if (b!=null)
        ret.add(b);
    }
    return ret;
  }

  private static Race convertToRace(RaceRow rr) {
    if (rr == null) {
      return null;
    }
    Race r = new Race();
    r.classId = rr.ClassId;
    r.coeff = rr.Coeff;
    r.courseId = rr.CourseId;
    r.discardable = rr.Discardable;
    r.distance = rr.Distance;
    r.provisional = rr.Provisional;
    r.orcRaceId = rr.RaceId;
    r.raceName = rr.RaceName;
    r.scoringType = rr.ScoringType;
    r.start = rr.StartTime;
    return r;
  }

  private static ArrayList<Boat> convertFleetToBoats(OrcscFile f) {
    ArrayList<Boat> ret = new ArrayList<>();
    if (f == null) {
      return ret;
    }
    for (FleetRow r : f.fleet.list) {
      Boat b = convertToBoat(r);
      ret.add(b);
    }

    return ret;
  }

  private static Boat convertToBoat(FleetRow r) {
    if (r == null) {
      return null;
    }
    Boat b = new Boat();
    b.setBowNo(r.BowNo);
    b.setLOA(r.LOA);
    b.setYachtsClass(r.YClass);
    b.setYachtsName(r.YachtName);
    b.setSailNo(r.SailNo);
    b.setRefNo(r.RefNo);
    b.setYachtId(r.YID);
    b.setClassId(r.ClassId);
    b.setDivId(r.DivId);
    b.setCdl(r.CDL);
    b.setGph(r.GPH);
    b.setOsn(r.OSN);
    b.setCtod(r.CTOD);
    b.setCtot(r.CTOT);
    b.setOwner(r.Owner);
    b.setSkipper(r.Skipper);
    b.setSponsor(r.Sponsor);
    b.setClub(r.Club);
    b.setNation(r.Nation);
    b.setEmail(r.EMail);
    b.setPhone(r.Phone);
    b.setCertType(r.CertType);
    b.setIssueDate(r.IssueDate);
    b.setTimeLimitSecs(r.TimeLimitSecs);
    b.setNatAuth(r.NatAuth);
    b.setBIN(r.BIN);
    b.setPoints(r.Points);
    b.setPosition(r.Position);
    b.setPtsHash(r.PtsHash);
    b.setPtsHash2(r.PtsHash2);
    b.setRms(r.RMS);
    b.setIlcwa(r.ILCWA);
    return b;
  }

  public static Event getEvent(String orcscString) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(orcscString);
      return converEventToEvent(f);
    } catch (Exception e) {
      Log.e(TAG, "Error deserializing orcsc file", e);
    }
    return null;
  }

  private static Event converEventToEvent(OrcscFile f) {
    Event e = new Event();
    e.name = f.event.row.EventName;
    return e;
  }
}
