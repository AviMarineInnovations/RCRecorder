package in.avimarine.orcscorerxmlparser;

import in.avimarine.orcscorerxmlparser.Orcsc.Event;
import in.avimarine.orcscorerxmlparser.Orcsc.EventRow;
import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import java.io.File;
import java.net.URL;
import junit.framework.Assert;
import org.junit.Test;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class OrcscSerializerTest {

  @Test
  public void serialize() {
    OrcscSerializer s = new OrcscSerializer();
    OrcscFile example = new OrcscFile();
    example.event = new Event();
    example.event.row = new EventRow();
    example.event.row.EventName = "New event";
    example.event.row.EventFolder = "event Folder";
    try {
      System.out.print(s.serialize(example));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void serialize1() {
    OrcscSerializer s = new OrcscSerializer();
    OrcscDeserializer d = new OrcscDeserializer();
    try {
      OrcscFile example = d.deserialize("test1.orcsc");
      System.out.print(s.serialize(example));
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  @Test
  public void serializeToFile() {
    OrcscSerializer s = new OrcscSerializer();
    OrcscDeserializer d = new OrcscDeserializer();
    File f = new File("eurotest16.orcsc");
    try {
      OrcscFile example = d.deserialize("euro2016.orcsc");
      s.serialize(example,f);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail();
    }
  }

  private static File getFileFromPath(Object obj, String fileName) {
    ClassLoader classLoader = obj.getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    return new File(resource.getPath());
  }
}