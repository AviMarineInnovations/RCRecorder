package in.avimarine.orcscorerxmlparser;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class EmptyElementConverter implements Converter<Integer> {

  @Override
  public Integer read(InputNode node) throws Exception {
    if (node.isEmpty()) {
      return null;
    } else {
      return Integer.valueOf(node.getValue());
    }
  }

  @Override
  public void write(OutputNode node, Integer value) throws Exception {
    if (value == null) {
      node.setValue(null);
    }
  }
}
