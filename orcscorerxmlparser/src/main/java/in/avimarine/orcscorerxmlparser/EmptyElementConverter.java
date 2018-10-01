package in.avimarine.orcscorerxmlparser;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class EmptyElementConverter implements Converter<Object>
{
  @Override
  public String read(InputNode node) throws Exception
  {
    /* Implement if required */
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void write(OutputNode node, Object value) throws Exception {

  }
}
