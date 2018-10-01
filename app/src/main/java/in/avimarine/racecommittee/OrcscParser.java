package in.avimarine.racecommittee;

import android.util.Log;
import in.avimarine.orcscorerxmlparser.Orcsc.FleetRow;
import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import in.avimarine.orcscorerxmlparser.OrcscDeserializer;
import in.avimarine.racecommittee.objects.Boat;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
class OrcscParser {

  private static final String TAG = "OrcscParser";
  public static ArrayList<Boat> getBoats(String res) {
    try {
      OrcscFile f = OrcscDeserializer.deserialize(res);

      return convertFleetToBoats(f);
    } catch (Exception e) {
      Log.e(TAG, "Error parsing orcsc file string.");
    }
    return null;
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
