package in.avimarine.orcscorerxmlparser.Orcsc;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
class Cls {
  @ElementList(inline = true)
  private List<ClsRow> list;
}
@Root (name = "ROW")
class ClsRow {
  @Element (required = false)
  String ClassId;
  @Element (required = false)
  String ClassName;
  @Element (required = false)
  int Discards;
  @Element (required = false)
  boolean DivFromOverall;
  @Element (required = false)
  String TimeLimitFormulae;
  @Element (required = false)
  int ResultScoring;
  @Element (required = false)
  boolean UseBoatIW;
}