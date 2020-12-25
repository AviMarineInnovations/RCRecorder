package in.avimarine.orcscorerxmlparser.Orcsc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class EventRow {
  @Element
  public String EventName;
  @Element (required = false)
  public String EventFolder;
  @Element (required = false)
  public String EventId;
  @Element (required = false)
  public String Status;
  @Element (required = false)
  public String StartDate;
  @Element (required = false)
  public String EndDate;
  @Element (required = false)
  public String Venue;
  @Element (required = false)
  public String Organizer;
  @Element (required = false)
  public String DoubleScoring;
  @Element (required = false)
  public String AlternativeRMS;
  @Element (required = false)
  public String TZAbbr;
  @Element (required = false)
  public String UTCOffset;
  @Element (required = false)
  public String EventTitle;
  @Element (required = false)
  public String EventCode;
  @Element (required = false)
  public String DSFormula;
}
