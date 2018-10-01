package in.avimarine.orcscorerxmlparser;

import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import java.io.File;
import java.net.URL;
import org.junit.Test;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class OrcscDeserializerTest {

  @Test
  public void deserialize() {
    OrcscDeserializer deserializer  = new OrcscDeserializer();
    try {
      File f = getFileFromPath(this,"test1.orcsc");
      OrcscFile o =  deserializer.deserialize(f);
      System.out.print(o.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void deserializeEurope16() {
    OrcscDeserializer deserializer  = new OrcscDeserializer();
    File f = getFileFromPath(this,"euro2016.orcsc");
    try {
      OrcscFile o =  deserializer.deserialize(f);
      System.out.print(o.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private static File getFileFromPath(Object obj, String fileName) {
    ClassLoader classLoader = obj.getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    return new File(resource.getPath());
  }
}