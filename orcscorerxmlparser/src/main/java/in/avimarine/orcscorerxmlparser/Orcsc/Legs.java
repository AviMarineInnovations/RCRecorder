package in.avimarine.orcscorerxmlparser.Orcsc;

import java.util.List;
import org.simpleframework.xml.ElementList;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
class Legs {
  @ElementList(inline = true, required = false)
  private List<LegRow> list;
}
