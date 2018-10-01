package in.avimarine.orcscorerxmlparser;

import java.text.DateFormat;
import java.util.Date;
import org.simpleframework.xml.transform.Transform;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class DateFormatTransformer implements Transform<Date>
{
  private DateFormat dateFormat;


  public DateFormatTransformer(DateFormat dateFormat)
  {
    this.dateFormat = dateFormat;
  }



  @Override
  public Date read(String value) throws Exception
  {
    return dateFormat.parse(value);
  }


  @Override
  public String write(Date value) throws Exception
  {
    return dateFormat.format(value);
  }

}
