package in.avimarine.racecommittee;

import android.support.annotation.Nullable;
import android.util.Log;
import in.avimarine.orcscorerxmlparser.Orcsc.FleetRow;
import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import in.avimarine.orcscorerxmlparser.Orcsc.RaceRow;
import in.avimarine.orcscorerxmlparser.OrcscDeserializer;
import in.avimarine.racecommittee.objects.Boat;
import in.avimarine.racecommittee.objects.Race;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Contract;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class OrcscParser {

  private static final String TAG = "OrcscParser";
  @Nullable
  public static ArrayList<Boat> getBoats(String res) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(res);

      return convertFleetToBoats(f);
    } catch (Exception e) {
      Log.e(TAG, "Error parsing orcsc file string.");
    }
    return null;
  }
  @Nullable
  public static ArrayList<Race> getRaces(String res) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(res);

      return convertRacesToRace(f);
    } catch (Exception e) {
      Log.e(TAG, "Error parsing orcsc file string.");
    }
    return null;
  }

  private static ArrayList<Race> convertRacesToRace(OrcscFile f) {
    ArrayList<Race> ret = new ArrayList<>();
    if (f==null) return ret;
    for (RaceRow r: f.race.list){
      Race b = convertToRace(r);
      ret.add(b);
    }

    return ret;
  }

  @Contract("null -> null")
  private static Race convertToRace(RaceRow rr) {
    if (rr==null)
      return null;
    Race r = new Race();
    r.ClassId = rr.ClassId;
    r.Coeff = rr.Coeff;
    r.CourseId = rr.CourseId;
    r.Discardable = rr.Discardable;
    r.Distance = rr.Distance;
    r.Provisional = rr.Provisional;
    r.RaceId = rr.RaceId;
    r.RaceName = rr.RaceName;
    r.ScoringType = rr.ScoringType;
    r.StartTime = rr.StartTime;
    return r;
  }

  private static ArrayList<Boat> convertFleetToBoats(OrcscFile f) {
    ArrayList<Boat> ret = new ArrayList<>();
    if (f==null) return ret;
    for (FleetRow r: f.fleet.list){
      Boat b = convertToBoat(r);
      ret.add(b);
    }

    return ret;
  }

  @Contract("null -> null")
  private static Boat convertToBoat(FleetRow r) {
    if (r==null)
      return null;
    Boat b = new Boat();
    b.setBowNo(r.BowNo);
    b.setLOA(r.LOA);
    b.setYachtsClass(r.YClass);
    b.setYachtsName(r.YachtName);
    b.setSailNo(r.SailNo);
    b.setRefNo(r.RefNo);
    return b;
  }
}
