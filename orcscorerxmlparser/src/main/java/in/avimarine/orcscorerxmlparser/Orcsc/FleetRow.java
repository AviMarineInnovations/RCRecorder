package in.avimarine.orcscorerxmlparser.Orcsc;

import in.avimarine.orcscorerxmlparser.EmptyElementConverter;
import java.util.Date;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
@Root(name = "ROW", strict = false)
public class FleetRow {
  @Element (required = false)
  public String SailNo;
  @Element (required = false)
  public String YachtName;
  @Element (required = false)
  public int BowNo;
  @Element (required = false)
  public String ClassId;
  @Element (required = false)
  public String DivId;
  @Element (required = false)
  public double LOA;
  @Element (required = false)
  public double CDL;
  @Element (required = false)
  public double GPH;
  @Element (required = false)
  public double OSN;
  @Element (required = false)
  public double CTOD;
  @Element (required = false)
  public double CTOT;
  @Element (required = false)
  public String YClass;
  @Element (required = false)
  public String Owner;
  @Element (required = false)
  public String Skipper;
  @Element (required = false)
  public String Sponsor;
  @Element (required = false)
  public String Club;
  @Element (required = false)
  public String Nation;
  @Element (required = false)
  public String EMail;
  @Element (required = false)
  public String Phone;
  @Element (required = false)
  public String CertType;
  @Element (required = false)
  public Date IssueDate;
  @Element (required = false)
  @Convert(EmptyElementConverter.class)
  public Integer TimeLimitSecs;
  @Element (required = false)
  public String NatAuth;
  @Element (required = false)
  public String BIN;
  @Element (required = false)
  public String RefNo;
  @Element (required = false)
  public double Points;
  @Element (required = false)
  public int Position;
  @Element (required = false)
  public String PtsHash;
  @Element (required = false)
  public String PtsHash2;
  @Element (required = false)
  public String RMS;
  @Element (required = false)
  public int YID;
  @Element (required = false)
  public double ILCWA;
}
