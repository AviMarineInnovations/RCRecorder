package in.avimarine.orcscorerxmlparser.Orcsc;

import java.util.Date;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
@Root(name = "ROW")
public class RsltRow {
  @Element (required = false)
  public double TOD;
  @Element (required = false)
  public int Finish;
  @Element (required = false)
  public Date FinishTime;
  @Element (required = false)
  public int CorrectedSeconds;
  @Element (required = false)
  public int RaceId;
  @Element (required = false)
  public int YID;
  @Element (required = false)
  public String Remarks;
  @Element (required = false)
  public double TOT;
  @Element (required = false)
  public double Penalty;
  @Element (required = false)
  public double IW;
  @Element (required = false)
  public int PosOvl;
  @Element (required = false)
  public double PtsOvl;
  @Element (required = false)
  public int PosCls;
  @Element (required = false)
  public double PtsCls;
  @Element (required = false)
  public boolean DiscardedOvl;
  @Element (required = false)
  public boolean DiscardedCls;
  @Element (required = false)
  public int CorrDelay;
}
