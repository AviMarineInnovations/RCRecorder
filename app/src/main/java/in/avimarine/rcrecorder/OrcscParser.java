package in.avimarine.rcrecorder;

import android.support.annotation.Nullable;
import android.util.Log;
import in.avimarine.orcscorerxmlparser.Orcsc.FleetRow;
import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import in.avimarine.orcscorerxmlparser.Orcsc.RaceRow;
import in.avimarine.orcscorerxmlparser.OrcscDeserializer;
import in.avimarine.rcrecorder.objects.Boat;
import in.avimarine.rcrecorder.objects.Event;
import in.avimarine.rcrecorder.objects.Race;
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

  private static ArrayList<Race> convertRacesToRace(OrcscFile f) {
    ArrayList<Race> ret = new ArrayList<>();
    if (f == null) {
      return ret;
    }
    for (RaceRow r : f.race.list) {
      Race b = convertToRace(r);
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
