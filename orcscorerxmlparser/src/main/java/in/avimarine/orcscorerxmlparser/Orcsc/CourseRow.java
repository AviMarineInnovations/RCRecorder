package in.avimarine.orcscorerxmlparser.Orcsc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
@Root(name = "ROW")
class CourseRow {
  @Element
  public int CourseNo;
  @Element
  public String CourseName;
  @Element
  public int CourseId;

}
