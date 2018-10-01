package in.avimarine.orcscorerxmlparser;

import in.avimarine.orcscorerxmlparser.Orcsc.OrcscFile;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

/**
 * This file is part of an Avi Marine Innovations project: RaceCommittee first created by aayaffe on
 * 01/10/2018.
 */
public class OrcscDeserializer {
  public static OrcscFile deserialize(File filename) throws Exception {

    // Maybe you have to correct this or use another / no Locale
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    RegistryMatcher m = new RegistryMatcher();
    m.bind(Date.class, new DateFormatTransformer(format));
    Serializer serializer = new Persister(m);
    return serializer.read(OrcscFile.class, filename);
  }
  public static OrcscFile deserialize(String s) throws Exception {

    // Maybe you have to correct this or use another / no Locale
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    RegistryMatcher m = new RegistryMatcher();
    m.bind(Date.class, new DateFormatTransformer(format));
    Serializer serializer = new Persister(m);
    return serializer.read(OrcscFile.class, s);
  }


}
