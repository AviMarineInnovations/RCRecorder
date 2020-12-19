package in.avimarine.orcscorerxmlparser.Orcsc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root (strict = false)
public class Event{
  @Element (name = "ROW")
  public EventRow row;
}

