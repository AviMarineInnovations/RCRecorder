package in.avimarine.orcscorerxmlparser.Orcsc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
@Root(name = "ROW")
class LegRow {
  @Element (required = false)
  public double Distance;
  @Element (required = false)
  public double Course;
  @Element (required = false)
  public double WindDirection;
  @Element (required = false)
  public double WindSpeed;
  @Element (required = false)
  public int PDCourse;
  @Element (required = false)
  public int CourseId;
}
