package in.avimarine.orcscorerxmlparser.Orcsc;

import in.avimarine.orcscorerxmlparser.EmptyElementConverter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.convert.Convert;

public class EventRow {
  @Element
  public String EventName;
  @Element (required = false)
  public String EventFolder;
}
