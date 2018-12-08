package in.avimarine.orcscorerxmlparser.Orcsc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */

@Root (name = "event")
public class OrcscFile {
  @Element (name = "Event")
  public Event event;
  @Element (name = "Fleet", required = false)
  public Fleet fleet;
  @Element (name = "Cls", required = false)
  public Cls cls;
  @Element (name = "Divs", required = false)
  public Divs divs;
  @Element (name = "Course", required = false)
  public Course course;
  @Element (name = "Race", required = false)
  public Race race;
  @Element (name = "Rslt", required = false)
  public Rslt rslt;
  @Element (name = "Legs", required = false)
  public Legs legs;
  @Element (name = "reports", required = false)
  public Reports reports;
}


