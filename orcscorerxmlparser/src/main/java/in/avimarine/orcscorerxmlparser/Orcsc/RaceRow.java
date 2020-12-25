package in.avimarine.orcscorerxmlparser.Orcsc;

import java.util.Date;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
@Root(name = "ROW", strict = false)
public class RaceRow {
  @Element (required = false)
  public int RaceId;
  @Element (required = false)
  public String RaceName;
  @Element (required = false)
  public Date StartTime;
  @Element (required = false)
  public String ClassId;
  @Element (required = false)
  public double Distance;
  @Element (required = false)
  public String CourseId;
  @Element (required = false)
  public boolean Provisional;
  @Element (required = false)
  public int ScoringType;
  @Element (required = false)
  public boolean Discardable;
  @Element (required = false)
  public double Coeff;
}
