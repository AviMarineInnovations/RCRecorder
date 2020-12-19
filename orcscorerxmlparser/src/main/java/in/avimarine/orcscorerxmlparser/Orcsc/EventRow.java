package in.avimarine.orcscorerxmlparser.Orcsc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class EventRow {
  @Element
  public String EventName;
  @Element (required = false)
  public String EventFolder;
}
